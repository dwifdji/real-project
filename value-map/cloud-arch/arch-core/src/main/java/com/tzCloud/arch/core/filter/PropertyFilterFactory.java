package com.tzCloud.arch.core.filter;

import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 *
*/ 
public class PropertyFilterFactory {
	private static Set<String> DEFAULT_FILTER_PROPS = new HashSet<String>();
	static {
		DEFAULT_FILTER_PROPS.add("endRow");
		DEFAULT_FILTER_PROPS.add("firstPage");
		DEFAULT_FILTER_PROPS.add("hasNextPage");
		DEFAULT_FILTER_PROPS.add("hasPreviousPage");
		DEFAULT_FILTER_PROPS.add("isFirstPage");
		DEFAULT_FILTER_PROPS.add("isLastPage");
		DEFAULT_FILTER_PROPS.add("lastPage");
		DEFAULT_FILTER_PROPS.add("navigateFirstPage");
		DEFAULT_FILTER_PROPS.add("navigateLastPage");
		DEFAULT_FILTER_PROPS.add("navigatePages");
		DEFAULT_FILTER_PROPS.add("navigatepageNums");
		DEFAULT_FILTER_PROPS.add("nextPage");
		DEFAULT_FILTER_PROPS.add("prePage");
		DEFAULT_FILTER_PROPS.add("startRow");
		DEFAULT_FILTER_PROPS.add("size");
		DEFAULT_FILTER_PROPS.add("pageNum");
		DEFAULT_FILTER_PROPS.add("pageSize");
		DEFAULT_FILTER_PROPS.add("orderBy");
	}

	/**
	 * 创建属性过滤器
	 * 
	 * @param props
	 *            需要过滤的属性名数组
	 * @return
	 */
	public static PropertyFilter create(final String[] props) {
		return new PropertyFilter() {
			@Override
			public boolean apply(Object object, String name, Object value) {
				boolean result = true;
				if (props != null && props.length > 0) {
					result = !DEFAULT_FILTER_PROPS.contains(name)
							&& !array2Set(props).contains(name);
				} else {
					result = !DEFAULT_FILTER_PROPS.contains(name);
				}
				return result;
			}
		};
	}

	/**
	 * @param props
	 * @return
	 */
	public static PropertyFilter createExtents(final String[] props) {
		return new PropertyFilter() {
			@Override
			public boolean apply(Object object, String name, Object value) {
				String className = object.getClass().getName();
				boolean result = true;
				if (props != null && props.length > 0) {
					if ("cn.memedai.cms.manager.template.Response"
							.equalsIgnoreCase(className)) {
						result = true;
					} else {
						result = !DEFAULT_FILTER_PROPS.contains(name)
								&& !array2Set(props).contains(name);
					}

				} else {
					result = !DEFAULT_FILTER_PROPS.contains(name);
				}
				return result;
			}
		};
	}

	private static Set<String> array2Set(String[] source) {
		Set<String> dist = new HashSet<String>();
		for (int i = 0; i < source.length; i++) {
			dist.add(source[i]);
		}
		return dist;
	}

}
