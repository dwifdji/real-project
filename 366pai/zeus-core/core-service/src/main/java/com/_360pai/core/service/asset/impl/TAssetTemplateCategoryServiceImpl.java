package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.asset.*;
import com._360pai.core.dao.asset.*;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.asset.req.TAssetTemplateCategoryReq;
import com._360pai.core.model.asset.*;
import com._360pai.core.service.asset.TAssetTemplateCategoryService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.list.TreeList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class TAssetTemplateCategoryServiceImpl implements TAssetTemplateCategoryService {

    @Resource
    private TAssetCategoryDao tAssetCategoryDao;
    @Resource
    private TAssetCategoryOptionDao tAssetCategoryOptionDao;
    @Resource
    private TAssetTemplateCategoryDao tAssetTemplateCategoryDao;
    @Resource
    private TAssetTemplateFieldGroupMapDao tAssetTemplateFieldGroupMapDao;
    @Resource
    private TAssetFieldItemDao tAssetFieldItemDao;
    @Resource
    private TAssetFieldFilterDao tAssetFieldFilterDao;
    @Resource
    private TAssetFieldFilterOptionDao tAssetFieldFilterOptionDao;
    @Resource
    private TAssetFieldFilterOptionItemDao tAssetFieldFilterOptionItemDao;
    @Resource
    private TAssetFieldFilterOptionItemDataDao tAssetFieldFilterOptionItemDataDao;
    @Resource
    private TAssetFieldModelDao tAssetFieldModelDao;
    @Resource
    private TAssetFieldModelOptionDao tAssetFieldModelOptionDao;
    @Resource
    private RedisCachemanager redisCachemanager;


    @Override
    public PageInfo TemplateCategoryList(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<TAssetTemplateCategory> tAssetTemplateCategories = tAssetTemplateCategoryDao.selectAll();
        for (TAssetTemplateCategory category : tAssetTemplateCategories) {
            TAssetCategoryCondition categoryCondition = new TAssetCategoryCondition();
            categoryCondition.setId(category.getCategoryId());
            TAssetCategory category1 = tAssetCategoryDao.selectOneResult(categoryCondition);
            category.setCategoryName(category1.getName());
        }
        return new PageInfo<>(tAssetTemplateCategories);
    }

    @Override
    public int addTemplateCategoryList(TAssetTemplateCategory params) {
        TAssetTemplateCategory tAssetTemplateCategoryByName = findTAssetTemplateCategoryByName(params);
        if (tAssetTemplateCategoryByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "模板名称已存在");
        }
        //判断是否有默认模板了
        hasDefault(params);
        return tAssetTemplateCategoryDao.insert(params);
    }

    @Override
    public int editTemplateCategory(TAssetTemplateCategory params) {
        TAssetTemplateCategory tAssetTemplateCategoryById = findTAssetTemplateCategoryById(params);
        if (tAssetTemplateCategoryById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的模板不存在");
        }
//        hasDefault(params);  //判断是否有默认模板了
        return tAssetTemplateCategoryDao.updateById(params);
    }

    @Override
    public int addTemplateGroup(Integer assetTemplateCategoryId, Integer[] groupIds) {
        int m = 0;
        for (Integer groupId : groupIds) {
            TAssetTemplateFieldGroupMap map = new TAssetTemplateFieldGroupMap();
            map.setAssetFieldGroupId(groupId);
            map.setAssetTemplateCategoryId(assetTemplateCategoryId);
            int insert = tAssetTemplateFieldGroupMapDao.insert(map);
            if (insert > 0) {
                m++;
            }
        }
        if (m != groupIds.length) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "添加分组字段失败");
        }
        return m;
    }

    @Override
    public Object getTemplateGroup(Integer paramsId) {
        return tAssetTemplateFieldGroupMapDao.getTemplateGroup(paramsId);
    }

    @Override
    public Object setTemplateGroupField(TAssetFieldItem params) {
        TAssetFieldItemCondition condition = new TAssetFieldItemCondition();
        //模板ID
        condition.setTemplateId(params.getTemplateId());
        //二级筛选器
        condition.setFilterOptionId(params.getFilterOptionId());
        //三级筛选器
        condition.setFilterOptionItemId(params.getFilterOptionItemId());
        //分组字段ID
        condition.setFieldGroupId(params.getFilterOptionItemId());
        List<TAssetFieldItem> tAssetFieldItems = tAssetFieldItemDao.selectList(condition);
        if (!tAssetFieldItems.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该字段已被添加");
        }
        return tAssetFieldItemDao.insert(params);
    }

    @Override
    public Object getTemplate(Integer categoryId) {

        Object o = redisCachemanager.get("template2");

        if (o != null) {
            return JSONArray.parse(o.toString());
        }

        //通过类型先去获取类型对应的模板ID
        TAssetTemplateCategoryCondition condition = new TAssetTemplateCategoryCondition();
        condition.setCategoryId(categoryId);
        List<TAssetTemplateCategory> tAssetTemplateCategories = tAssetTemplateCategoryDao.selectList(condition);
        TAssetTemplateCategory tAssetTemplateCategory;
        tAssetTemplateCategory = getTemplate(condition, null, null, tAssetTemplateCategories, tAssetTemplateCategories.get(0));
        if (tAssetTemplateCategory == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "获取表格出错请联系客服");
        }
        //模板ID
        Integer templateCategoryId = tAssetTemplateCategory.getId();
        //获取模板下有的所有分组字段
        List<Map> fieldGroupMaps = tAssetTemplateFieldGroupMapDao.getTemplateGroup(templateCategoryId);
        if (fieldGroupMaps.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "展示表格出错请联系客服");
        }

        JSONArray jsonArray = new JSONArray();
        for (Map fieldGroupMap : fieldGroupMaps) {
            int fieldGroupId = (Integer) fieldGroupMap.get("assetFieldGroupId");
            List<Map> maps = tAssetFieldItemDao.findFields(templateCategoryId, fieldGroupId, null, null, null);
            Set<Integer> filterIds = new HashSet<>();
            for (Map map : maps) {
                filterIds.add((Integer) map.get("filterId"));
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for (Integer id : filterIds) {
                TAssetFieldFilterCondition condition1 = new TAssetFieldFilterCondition();
                if (id == null) {
                    continue;
                }
                condition1.setId(id);
                TAssetFieldFilter tAssetFieldFilter = tAssetFieldFilterDao.selectOneResult(condition1);
                Map<String, Object> map = new HashMap<>(16);
                map.put("extensible", tAssetFieldFilter.getExtensible());
                map.put("id", tAssetFieldFilter.getId());
                map.put("name", tAssetFieldFilter.getName());
                map.put("orderNum", tAssetFieldFilter.getOrderNum());
                map.put("filterKey", tAssetFieldFilter.getFilterKey());
                //如果是可扩展的筛选器 需要增加标题
                if (tAssetFieldFilter.getExtensible().equals(1)) {
                    map.put("title", tAssetFieldFilter.getName());
                }
                //查询二级筛选器
                TAssetFieldFilterOptionCondition fieldFilterOptionCondition = new TAssetFieldFilterOptionCondition();
                fieldFilterOptionCondition.setFilterId(id);
                List<TAssetFieldFilterOption> tAssetFieldFilterOptions = tAssetFieldFilterOptionDao.selectList(fieldFilterOptionCondition);
                for (TAssetFieldFilterOption option : tAssetFieldFilterOptions) {
                    List<Map> fields = tAssetFieldItemDao.findFields(templateCategoryId, fieldGroupId, option.getId(), null, null);
                    List<Map> mapList = new ArrayList<>();
                    for (Map map1 : fields) {
                        Object object = map1.get("filterOptionItemId");
                        if (object == null) {
                            mapList.add(map1);
                        }
                    }
                    option.setFields(mapList);

                    //查询三级筛选器
                    TAssetFieldFilterOptionItemCondition tAssetFieldFilterOptionItemCondition = new TAssetFieldFilterOptionItemCondition();
                    tAssetFieldFilterOptionItemCondition.setFilterId(id);
                    List<TAssetFieldFilterOptionItem> tAssetFieldFilterOptionItems = tAssetFieldFilterOptionItemDao.selectList(tAssetFieldFilterOptionItemCondition);

                    for (TAssetFieldFilterOptionItem item : tAssetFieldFilterOptionItems) {
                        List<Map> fieldss = tAssetFieldItemDao.findFields(templateCategoryId, fieldGroupId, option.getId(), item.getId(), null);
                        item.setMaps(fieldss);
                    }

                    for (TAssetFieldFilterOptionItem item : tAssetFieldFilterOptionItems) {
                        if (item.getMaps().isEmpty()) {
                            option.setItems(new ArrayList<>());
                        } else {
                            option.setItems(tAssetFieldFilterOptionItems);
                        }
                    }
                    if (tAssetFieldFilterOptionItems.isEmpty()) {
                        option.setItems(tAssetFieldFilterOptionItems);
                    }
                }


                map.put("options", tAssetFieldFilterOptions);
                list.add(map);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("assetFieldGroupName", fieldGroupMap.get("assetFieldGroupName"));
            Object object = fieldGroupMap.get("extensible");
            //如果是可扩展的分组 需要增加标题
            if (object != null && object.equals(1)) {
                jsonObject.put("assetFieldGroupTitle", fieldGroupMap.get("assetFieldGroupName"));
            }
            jsonObject.put("assetFieldGroupExtensible", fieldGroupMap.get("extensible"));
            List<Map> fields = tAssetFieldItemDao.findFieldsNotHaveOption(templateCategoryId, fieldGroupId);
            List<Map> displayField = tAssetFieldItemDao.findDisplayField(templateCategoryId, fieldGroupId);
            jsonObject.put("assetFieldGroupFields", fields);
            jsonObject.put("assetDisplayFieldGroupFields", displayField);
            jsonObject.put("assetFieldGroupFilters", list);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public Object getTemplateCopy(TAssetTemplateCategoryReq req) {
        Integer categoryId = req.getCategoryId();
        Integer categoryOptionId = req.getCategoryOptionId();
        log.info("请求模板数据的 请求参数为一级类型为：{}，二级类型为：{}", categoryId, categoryOptionId);
        /*String templateKey = "template";
        Object o = redisCachemanager.get(templateKey + categoryId);
        if (o != null) {
            return JSONArray.parse(o.toString());
        }*/


        //通过类型先去获取类型对应的模板ID
        TAssetTemplateCategoryCondition condition = new TAssetTemplateCategoryCondition();
        condition.setCategoryId(categoryId);
        if (categoryOptionId != null) {
            condition.setCategoryOptionId(categoryOptionId);
        }
        List<TAssetTemplateCategory> tAssetTemplateCategories = tAssetTemplateCategoryDao.selectList(condition);
        log.info("模板数据为：{}", JSON.toJSONString(tAssetTemplateCategories));
        if (tAssetTemplateCategories.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "获取字段出错请联系客服");
        }
        TAssetTemplateCategory tAssetTemplateCategory;
        tAssetTemplateCategory = getTemplate(condition, tAssetTemplateCategories, null, null, tAssetTemplateCategories.get(0));
        if (tAssetTemplateCategory == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "获取字段出错请联系客服");
        }
        log.info("查询的模板信息为：{}", tAssetTemplateCategory);
        //模板ID
        Integer templateCategoryId = tAssetTemplateCategory.getId();
        //获取模板下有的所有分组字段
        List<Map> fieldGroupMaps = tAssetTemplateFieldGroupMapDao.getTemplateGroup(templateCategoryId);
        if (fieldGroupMaps.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "展示表格出错请联系客服");
        }

        JSONArray jsonArray = new JSONArray();
        for (Map fieldGroupMap : fieldGroupMaps) {
            int fieldGroupId = (Integer) fieldGroupMap.get("assetFieldGroupId");
            //获取当前分组下的所有字段
            List<Map> maps = tAssetFieldItemDao.findFields(templateCategoryId, fieldGroupId, null, null, null);

            List<Map> mapList = new TreeList<>();
            List<Map> mapModelList = new TreeList<>();
            List<Map> mapModelAndOptionList = new TreeList<>();
            Set<Integer> ids = new HashSet<>();
            Set<Integer> itemData = new HashSet<>();
            boolean hasMode = false;
            for (Map map : maps) {
                Object object = map.get("filterOptionId");
                if (SideType.AGENCY.getKey().equals(req.getSideType().getKey())) {
                    if ("委托人名称".equals(map.get("fieldName"))) {
                        map.put("fieldName","拍卖机构名称");
                    } else if ("委托人证件号码".equals(map.get("fieldName"))) {
                        map.put("fieldName","拍卖机构统一信用代码");
                    }
                }
                if (null == object) {
                    //一级模块
                    Integer modelId = (Integer) map.get("modelId");
                    //二级模块
                    Integer modelOptionId = (Integer) map.get("modelOptionId");
                    if (modelId != null) {
                        if (modelOptionId != null) {
                            mapModelAndOptionList.add(map);
                            mapModelList.add(map);
                            hasMode = true;
                        } else {
                            mapModelList.add(map);
                        }
                    } else {
                        mapList.add(map);
                    }
                } else {
                    //一级筛选器ID
                    Integer filterId = (Integer) map.get("filterId");
                    //二级筛选器ID
                    Integer filterOptionItemDataId = (Integer) map.get("filterOptionItemDataId");
                    //一级模块
                    Integer modelId = (Integer) map.get("modelId");
                    //二级模块
                    Integer modelOptionId = (Integer) map.get("modelOptionId");

                    if (modelId != null) {
                        if (modelOptionId != null) {
                            Map<String, Object> fmap = makeFields(req, templateCategoryId, fieldGroupId, ids, itemData, map, filterId, filterOptionItemDataId);
                            if (fmap == null) {
                                continue;
                            }
                            mapModelAndOptionList.add(fmap);
                            mapModelList.add(map);
                            hasMode = true;
                        } else {
                            Map<String, Object> fmap = makeFields(req, templateCategoryId, fieldGroupId, ids, itemData, map, filterId, filterOptionItemDataId);
                            if (fmap == null) {
                                continue;
                            }
                            mapModelList.add(fmap);
                        }
                    } else {
                        Map<String, Object> fmap = makeFields(req, templateCategoryId, fieldGroupId, ids, itemData, map, filterId, filterOptionItemDataId);
                        if (fmap == null) {
                            continue;
                        }
                        mapList.add(fmap);
                    }
                }
            }

            MokeData mokeData = new MokeData(mapList, mapModelList, mapModelAndOptionList, hasMode).invoke();
            JSONObject jsonObject = mokeData.getJsonObject();
            JSONArray jsonArray1 = mokeData.getJsonArray1();
            jsonObject.put("mapModelList", jsonArray1);
            jsonObject.put("assetFieldGroupId", fieldGroupMap.get("assetFieldGroupId"));

            jsonObject.put("assetFieldGroupTitle", fieldGroupMap.get("assetFieldGroupTitle"));

            if (req.getSideType().getKey().equals(SideType.AGENCY.getKey())) {
                if ("委托方信息".equals(fieldGroupMap.get("assetFieldGroupName"))) {
                    jsonObject.put("groupName", "拍卖机构信息");
                } else {
                    jsonObject.put("groupName", fieldGroupMap.get("assetFieldGroupName"));
                }
            } else {
                jsonObject.put("groupName", fieldGroupMap.get("assetFieldGroupName"));
            }

            if (fieldGroupMap.get("assetFieldGroupName").equals("拍品信息") ||
                    fieldGroupMap.get("assetFieldGroupName").equals("招商信息")) {
                jsonObject.put("hideTitle", false);
            } else {
                jsonObject.put("hideTitle", true);
            }
            jsonObject.put("groupExtensible", fieldGroupMap.get("extensible"));

            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fields", jsonArray);
        jsonObject.put("templateId", templateCategoryId);
//        redisCachemanager.set(templateKey + categoryId, jsonArray.toString());
        return jsonObject;
    }

    @Override
    public Object getTemplateParty(Integer categoryId, Integer partyId) {
        return null;
    }

    @Override
    public TAssetTemplateCategory selectByTemplateId(Integer templateId) {

        TAssetTemplateCategory templateCategory = tAssetTemplateCategoryDao.selectById(templateId);
        log.info("查询模板的返回结果为：{}", JSON.toJSONString(templateCategory));
        if (templateCategory == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "模板不存在");
        }
        Integer categoryId = templateCategory.getCategoryId();
        Integer categoryOptionId = templateCategory.getCategoryOptionId();

        TAssetCategory category = tAssetCategoryDao.selectById(categoryId);
        if (category == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "参数不存在");
        }

        if (categoryOptionId != 0) {
            TAssetCategoryOption categoryOption = tAssetCategoryOptionDao.selectById(categoryOptionId);
            if (categoryOption == null) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "参数不存在");
            }
            templateCategory.setCategoryOptionName(categoryOption.getName());
        }

        templateCategory.setCategoryName(category.getName());
        return templateCategory;
    }

    @Override
    public TAssetTemplateCategory getAssetTemplateCategoryById(Integer categoryId) {
        TAssetTemplateCategory category = new TAssetTemplateCategory();
        category.setId(categoryId);
        return findTAssetTemplateCategoryById(category);
    }

    private TAssetTemplateCategory getTemplate(TAssetTemplateCategoryCondition condition, List<TAssetTemplateCategory> tAssetTemplateCategories, TAssetTemplateCategory tAssetTemplateCategory, List<TAssetTemplateCategory> o, TAssetTemplateCategory tAssetTemplateCategory2) {
        if (o != tAssetTemplateCategories) {
            //判断是否有默认模板
            condition.setIsGroupDefault(1);
            List<TAssetTemplateCategory> list = tAssetTemplateCategoryDao.selectList(condition);
            if (list.isEmpty()) {
                //当没有默认模板的时候取第一个
                tAssetTemplateCategory = tAssetTemplateCategory2;
            } else {
                tAssetTemplateCategory = list.get(0);
            }
        }
        return tAssetTemplateCategory;
    }

    private Map<String, Object> makeFields(TAssetTemplateCategoryReq req, Integer templateCategoryId, int fieldGroupId, Set<Integer> integers, Set<Integer> ids, Map map, Integer filterId, Integer filterOptionItemDataId) {
        Map<String, Object> fmap = findAssetFieldFilterByTemp(ids, integers, map, filterId, filterOptionItemDataId);
        if (fmap == null) {
            return null;
        }
        //查询二级筛选器
        TAssetFieldFilterOptionCondition fieldFilterOptionCondition = new TAssetFieldFilterOptionCondition();
        fieldFilterOptionCondition.setFilterId(filterId);
        PageHelper.orderBy("search_weight asc");
        List<TAssetFieldFilterOption> tAssetFieldFilterOptions = tAssetFieldFilterOptionDao.selectList(fieldFilterOptionCondition);
        getFilterOption(req, templateCategoryId, fieldGroupId, map, tAssetFieldFilterOptions, fmap);
        fmap.put("options", tAssetFieldFilterOptions);
        return fmap;
    }

    private Map<String, Object> findAssetFieldFilterByTemp(Set<Integer> integers, Set<Integer> ids, Map map, Integer filterId, Integer filterOptionItemDataId) {
        TAssetFieldFilterCondition condition1 = new TAssetFieldFilterCondition();
        if (filterId == null) {
            return null;
        }
        condition1.setId(filterId);
        TAssetFieldFilter tAssetFieldFilter = tAssetFieldFilterDao.selectOneResult(condition1);
        Map<String, Object> fmap = new HashMap<>();
        fmap.put("extensible", tAssetFieldFilter.getExtensible());
        fmap.put("id", tAssetFieldFilter.getId());
        fmap.put("name", tAssetFieldFilter.getName());
        fmap.put("type", tAssetFieldFilter.getType());
        fmap.put("modelTitle", map.get("modelTitle"));
        fmap.put("modelKey", map.get("modelKey"));
        fmap.put("modelExtensible", map.get("modelExtensible"));
        fmap.put("modelOptionName", map.get("modelOptionName"));
        fmap.put("modelOptionExtensible", map.get("modelOptionExtensible"));
        fmap.put("orderNum", tAssetFieldFilter.getOrderNum());
        fmap.put("filterKey", tAssetFieldFilter.getFilterKey());

        if (!ids.add(filterId)) {
            return null;
        }
        return fmap;
    }

    /*private Map<String, Object> mackModelFields(List<Map> mapModelList) {
        Set<String> title = new HashSet<>();
        Map<String, Object> map2 = new TreeMap();
        JSONObject jsmap2 = new JSONObject();
        for (Map map1 : mapModelList) {
            String title1 = (String) map1.get("modelTitle");
            if (title.add(title1)) {
                jsmap2.put(title1, new TreeList<>());
                jsmap2.put("modelExtensible", map1.get("modelExtensible"));
            }
        }
        JSONObject jsonObject1 = new JSONObject();
        for (Map map : mapModelList) {
            String filterKey = (String) map.get("modelTitle");
            List<Map> mapList1 = (List<Map>) jsmap2.get(map.get("modelTitle"));
            mapList1.add(map);

            jsonObject1.put("modelFields", mapList1);
            map2.put(filterKey, jsonObject1);
            map2.put("modelExtensible", jsmap2.get("modelExtensible"));
        }
        return map2;
    }*/

    private void getFilterOption(TAssetTemplateCategoryReq req, Integer templateCategoryId, int fieldGroupId, Map map, List<TAssetFieldFilterOption> tAssetFieldFilterOptions, Map<String, Object> fmap) {
        for (TAssetFieldFilterOption option : tAssetFieldFilterOptions) {
            List<Map> fields = tAssetFieldItemDao.findFields(templateCategoryId, fieldGroupId, option.getId(), null, null);
            List<Map> mapList1 = new ArrayList<>();
            List<Map> hideOptionMap = new ArrayList<>();
            for (Map map1 : fields) {
                Object object1 = map1.get("filterOptionItemId");
                if (SideType.AGENCY.equals(req.getSideType()) || SideType.ADMIN.equals(req.getSideType())) {
                    if (SideType.Operate.EDIT.getKey().equals(req.getOperate().getKey())) {
                        if ("reservePrice".equals(map1.get("filterKey"))) {
                            //机构和admin隐藏保证金
                            map1.put("ensconce", true);
                        }
                    }
                }
                if (object1 == null) {
                    fmap.put("filterOptionItemDataId", map.get("filterOptionItemDataId"));
                    fmap.put("displayed", map.get("displayed"));
                    fmap.put("partyFilterOptionId", map.get("partyFilterOptionId"));
                    mapList1.add(map1);
                }
            }
            option.setFields(mapList1);
            option.setHideList(hideOptionMap);

            //查询三级筛选器
            TAssetFieldFilterOptionItemCondition tAssetFieldFilterOptionItemCondition = new TAssetFieldFilterOptionItemCondition();
            tAssetFieldFilterOptionItemCondition.setFilterOptionId(option.getId());
            PageHelper.orderBy("order_num asc");
            List<TAssetFieldFilterOptionItem> tAssetFieldFilterOptionItems = tAssetFieldFilterOptionItemDao.selectList(tAssetFieldFilterOptionItemCondition);

            for (TAssetFieldFilterOptionItem item : tAssetFieldFilterOptionItems) {
                List<Map> fieldss = tAssetFieldItemDao.findFields(templateCategoryId, fieldGroupId, option.getId(), item.getId(), null);
                List<Map> mapList2 = new ArrayList<>();
                List<Map> hideItemMap = new ArrayList<>();
                for (Map map1 : fieldss) {
                    Object object1 = map1.get("filterOptionItemDataId");
                    if (object1 == null) { //|| item.getFilterId().equals(map.get("filterId"))
                        fmap.put("filterOptionItemDataId", map.get("filterOptionItemDataId"));
                        fmap.put("displayed", map.get("displayed"));
                        fmap.put("partyFilterOptionId", map.get("partyFilterOptionId"));
                        mapList2.add(map1);
                    }
                }
                item.setMaps(mapList2);
                item.setHideList(hideItemMap);

                //查询四级筛选器
                TAssetFieldFilterOptionItemDataCondition tAssetFieldFilterOptionItemDataCondition = new TAssetFieldFilterOptionItemDataCondition();
                tAssetFieldFilterOptionItemDataCondition.setFilterOptionItemId(item.getId());
                List<TAssetFieldFilterOptionItemData> tAssetFieldFilterOptionItemData = tAssetFieldFilterOptionItemDataDao.selectList(tAssetFieldFilterOptionItemDataCondition);

                for (TAssetFieldFilterOptionItemData dataMap : tAssetFieldFilterOptionItemData) {
                    List<Map> fields1 = tAssetFieldItemDao.findFields(templateCategoryId, fieldGroupId, option.getId(), item.getId(), dataMap.getId());
                    List<Map> mapList = new TreeList<>();
                    List<Map> hideList = new TreeList<>();
                    for (Map map1 : fields1) {
                        Integer desplayed = (Integer) map1.get("displayed");
                        if (desplayed != null && desplayed == 1) {
                            hideList.add(map1);
                        } else {
                            mapList.add(map1);
                        }
                    }
                    dataMap.setHideList(hideList);
                    dataMap.setDatas(mapList);
                    fmap.put("filterOptionItemDataId", null);
                    fmap.put("partyFilterOptionId", map.get("partyFilterOptionId"));
                    fmap.put("displayed", map.get("displayed"));
                    fmap.put("optionType", true);
                }
                item.setItemDatas(tAssetFieldFilterOptionItemData);
            }
            option.setItems(tAssetFieldFilterOptionItems);
        }
    }

    private TAssetTemplateCategory findTAssetTemplateCategoryByName(TAssetTemplateCategory params) {
        TAssetTemplateCategoryCondition categoryCondition = new TAssetTemplateCategoryCondition();
        categoryCondition.setTemplateName(params.getTemplateName());
        return tAssetTemplateCategoryDao.selectOneResult(categoryCondition);
    }

    private TAssetTemplateCategory findTAssetTemplateCategoryById(TAssetTemplateCategory params) {
        TAssetTemplateCategoryCondition categoryCondition = new TAssetTemplateCategoryCondition();
        categoryCondition.setId(params.getId());
        return tAssetTemplateCategoryDao.selectOneResult(categoryCondition);
    }

    private void hasDefault(TAssetTemplateCategory params) {
        TAssetTemplateCategoryCondition categoryCondition = new TAssetTemplateCategoryCondition();
        categoryCondition.setCategoryId(params.getCategoryId());
        categoryCondition.setIsGroupDefault(params.getIsGroupDefault());
        List<TAssetTemplateCategory> tAssetTemplateCategories = tAssetTemplateCategoryDao.selectList(categoryCondition);
        if (!tAssetTemplateCategories.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该类型已存在默认模板");
        }
    }


    private class MokeData {
        private List<Map> mapList;
        private boolean hasMode;
        private List<Map> mapModelList;
        private List<Map> mapModelAndOptionList;
        private JSONObject jsonObject;
        private JSONArray jsonArray1;

        MokeData(List<Map> mapList, List<Map> mapModelList, List<Map> mapModelAndOptionList, boolean hasMode) {
            this.mapList = mapList;
            this.mapModelList = mapModelList;
            this.hasMode = hasMode;
            this.mapModelAndOptionList = mapModelAndOptionList;
        }

        private JSONObject getJsonObject() {
            return jsonObject;
        }

        private JSONArray getJsonArray1() {
            return jsonArray1;
        }

        private MokeData invoke() {
            jsonObject = new JSONObject();
            List<Map> hidMap = new TreeList<>();
            List<Map> mapList5 = new TreeList<>();

            Map<Integer, List<Map>> mapTreeMap = new TreeMap<>();
            Map<Integer, List<Map>> mapHideMap = new TreeMap<>();
            for (Map map : mapList) {
                Integer displayed = (Integer) map.get("displayed");
                if (displayed != null && displayed == 1) {
                    hidMap.add(map);
                } else {
                    mapList5.add(map);
                }
            }

            CopyOnWriteArrayList<Map> mapcowlist = null;
            if (null != mapList5 && !mapList5.isEmpty()) {
                mapcowlist = new CopyOnWriteArrayList<>(mapList5);
                for (Map map : mapcowlist) {
                    Boolean optionType = (Boolean) map.get("optionType");
                    Integer partyFilterOptionId = (Integer) map.get("partyFilterOptionId");
                    if (partyFilterOptionId != null) {
                        for (Map map1 : mapList5) {
                            List<TAssetFieldFilterOption> options = (List<TAssetFieldFilterOption>) map1.get("options");
                            for (TAssetFieldFilterOption option : options) {
                                Integer id = option.getId();
                                if (id.equals(partyFilterOptionId)) {
                                    List<Map> fields = option.getFields();
                                    fields.add(map);
                                    mapcowlist.remove(map);
                                }
                            }
                        }
                    }
                    if (null != optionType && optionType) {
                        List<TAssetFieldFilterOption> options = (List<TAssetFieldFilterOption>) map.get("options");
                        for (TAssetFieldFilterOption option : options) {
                            List<TAssetFieldFilterOptionItem> items = option.getItems();
                            for (TAssetFieldFilterOptionItem item : items) {
                                List<TAssetFieldFilterOptionItemData> itemDatas = item.getItemDatas();
                                for (int i = 0; i < itemDatas.size(); i++) {
                                    TAssetFieldFilterOptionItemData itemMap = itemDatas.get(0);
                                    Integer id = itemMap.getId();
                                    List<Map> mapList2 = mapTreeMap.get(id);
                                    List<Map> hideList = mapHideMap.get(id);
                                    itemMap.setMapData(mapList2);
                                    itemMap.setHideList(hideList);
                                }
                            }
                        }
                    }
                }
            }

            if (mapcowlist != null) {
                jsonObject.put("mapList", mapcowlist);
            }

            jsonObject.put("hideMap", hidMap);
            jsonObject.put("mapModelList", mapModelList);
            //需要对一级分类再次进行筛选
            Set<String> title = new HashSet<>();
            Map<String, Object> map2 = new TreeMap<>();
            jsonArray1 = new JSONArray();
            TreeMap<String, Object> jsmap2 = new TreeMap<>();
            for (Map map1 : mapModelList) {
                String title1 = (String) map1.get("modelKey");
                if (title.add(title1)) {
                    jsmap2.put(title1, new TreeList<>());
                    jsmap2.put(title1 + "modelExtensible", map1.get("modelExtensible"));
                    jsmap2.put(title1 + "modelTitle", map1.get("modelTitle"));
                    jsmap2.put(title1 + "modelKey", map1.get("modelKey"));
                }
            }
            //模块中的筛选器
            List<Map> mapData = new TreeList<>();
            //模块中隐藏域
            List<Map> hideData = new TreeList<>();
            Map<Integer, List<Map>> treeMap = new TreeMap<>();
            Map<Integer, List<Map>> hideMap = new TreeMap<>();
            for (String key : jsmap2.keySet()) {
                TAssetFieldModelCondition condition1 = new TAssetFieldModelCondition();
                condition1.setModelKey(key);
                TAssetFieldModel tAssetFieldModel = tAssetFieldModelDao.selectOneResult(condition1);
                JSONObject jsonObject1 = new JSONObject();
                for (Map map : mapModelList) {
                    String filterKey = (String) map.get("modelKey");
                    if (!key.equals(filterKey)) {
                        continue;
                    }
                    Integer integer = (Integer) map.get("filterOptionItemDataId");
                    Integer displayed = (Integer) map.get("displayed");
                    if (integer != null) {
                        if (displayed != null && displayed == 1) {
                            hideData.add(map);
                            hideMap.put(integer, hideData);
                        } else {
                            mapData.add(map);
                            treeMap.put(integer, mapData);
                        }
                        continue;
                    }
                    List<Map> mapList1 = (List<Map>) jsmap2.get(map.get("modelKey"));
                    mapList1.add(map);

                    jsonObject1.put("modelFields", mapList1);
                    jsonObject1.put("modelExtensible", jsmap2.get(key + "modelExtensible"));
                    jsonObject1.put("modelTitle", jsmap2.get(key + "modelTitle"));
                    jsonObject1.put("modelKey", jsmap2.get(key + "modelKey"));
                    jsonObject1.put("modelId", tAssetFieldModel.getId());
                    jsonObject1.put("showTitle", tAssetFieldModel.getShowTitle());
                    Integer id = tAssetFieldModel.getId();
                    TAssetFieldModelOption tAssetFieldModelOption = tAssetFieldModelOptionDao.selectById(id);
                    if (tAssetFieldModelOption != null) {
                        jsonObject1.put("modelOptionId", tAssetFieldModelOption.getId());
                    }
                    map2.put(filterKey, jsonObject1);
                }
                List<Map> mapList1 = (List<Map>) jsonObject1.get("modelFields");
                CopyOnWriteArrayList<Map> cowList = null;
                if (null != mapList1 && !mapList1.isEmpty()) {
                    cowList = new CopyOnWriteArrayList<>(mapList1);
                    for (Map map : cowList) {
                        Boolean optionType = (Boolean) map.get("optionType");
                        Integer partyFilterOptionId = (Integer) map.get("partyFilterOptionId");
                        if (partyFilterOptionId != null) {
                            for (Map map1 : mapList1) {
                                List<TAssetFieldFilterOption> options = (List<TAssetFieldFilterOption>) map1.get("options");
                                if (options == null) {
                                    continue;
                                }
                                for (TAssetFieldFilterOption option : options) {
                                    Integer id = option.getId();
                                    if (id.equals(partyFilterOptionId)) {
                                        List<Map> fields = option.getFields();
                                        fields.add(map);
                                        cowList.remove(map);
                                    }
                                }
                            }
                        }
                        if (null != optionType && optionType) {
                            List<TAssetFieldFilterOption> options = (List<TAssetFieldFilterOption>) map.get("options");
                            for (TAssetFieldFilterOption option : options) {
                                List<TAssetFieldFilterOptionItem> items = option.getItems();
                                for (TAssetFieldFilterOptionItem item : items) {
                                    List<TAssetFieldFilterOptionItemData> itemDatas = item.getItemDatas();
                                    for (int i = 0; i < itemDatas.size(); i++) {
                                        TAssetFieldFilterOptionItemData itemMap = itemDatas.get(i);
                                        Integer id = itemMap.getId();
                                        List<Map> treeMapList = treeMap.get(id);
                                        List<Map> hideList = hideMap.get(id);

                                        List<Map> newTreeMapList = new TreeList<>();
                                        List<Map> newHideList = new TreeList<>();
                                        if (treeMapList != null) {
                                            makeMap(id, treeMapList, newTreeMapList);
                                        }
                                        List<Map> hideMapList = itemMap.getHideList();
                                        if (hideList != null) {
                                            makeMap(id, hideList, newHideList);
                                            hideMapList.addAll(newHideList);
                                            itemMap.setHideList(hideMapList);
                                        } else {
                                            itemMap.setHideList(hideMapList);
                                        }
                                        itemMap.setMapData(newTreeMapList);
                                    }
                                }
                            }
                        }
                    }
                }

                if (null != cowList) {
                    jsonObject1.put("modelFields", cowList);
                }
                if (!jsonObject1.isEmpty()) {
                    jsonArray1.add(jsonObject1);
                }
            }
            for (String key : jsmap2.keySet()) {
                for (Map map : mapModelAndOptionList) {
                    String filterKey = (String) map.get("modelKey");
                    if (key.equals(filterKey)) {
                        JSONObject object = (JSONObject) map2.get(key);
                        object.put("mapModelAndOptionList", mapModelAndOptionList);
                        if (hasMode) {
                            List<Map> mapList1 = (List<Map>) object.get("modelFields");
                            for (Map map1 : mapModelAndOptionList) {
                                List<TAssetFieldFilterOption> options = (List<TAssetFieldFilterOption>) map1.get("options");
                                if (options != null && !options.isEmpty()) {
                                    for (TAssetFieldFilterOption option : options) {
                                        List<TAssetFieldFilterOptionItem> items = option.getItems();
                                        if (items == null || items.isEmpty()) {
                                            List<Map> fields = option.getFields();
                                            if (fields != null && !fields.isEmpty()) {
                                                for (Map map5 : fields) {
                                                    mapList1.remove(map5);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    mapList1.remove(map1);
                                }
                            }
                            object.put("modelFields", mapList1);
                        }
                        break;
                    }
                }
            }
            return this;
        }

        private void makeMap(Integer id, List<Map> mapList2, List<Map> mapList3) {
            for (Map map1 : mapList2) {
                Integer filterOptionItemDataId = (Integer) map1.get("filterOptionItemDataId");
                if (filterOptionItemDataId != null) {
                    if (filterOptionItemDataId.equals(id)) {
                        mapList3.add(map1);
                    }
                }
            }
        }
    }
}