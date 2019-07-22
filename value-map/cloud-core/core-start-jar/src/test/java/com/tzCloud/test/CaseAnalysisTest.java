package com.tzCloud.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.model.perceptron.PerceptronNERecognizer;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.dao.caseMatching.CaseHtmlAnalysisDao;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.facade.caseMatching.CaseHtmlAnalysisFacade;
import com.tzCloud.core.facade.caseMatching.CaseHtmlDsrxxFacade;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.service.CaseHtmlAnalysisService;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaolei
 * @create 2019-03-19 16:35
 */
public class CaseAnalysisTest extends TestBase{

    @Resource
    private CaseHtmlAnalysisService caseHtmlAnalysisService;
    @Resource
    private CaseHtmlAnalysisFacade caseHtmlAnalysisFacade;
    @Resource
    private CaseHtmlDsrxxFacade caseHtmlDsrxxFacade;
    @Resource
    private CaseHtmlAnalysisDao caseHtmlAnalysisDao;
    @Resource
    private CaseHtmlDataDao caseHtmlDataDao;
    WenshuHtmlSaveUntil wenshuHtmlSaveUntil = new WenshuHtmlSaveUntil();


    @Test
    public void lawyerSaveTest() throws Exception
    {
        PageHelper.startPage(1, 10000);
        List<CaseHtmlData> htmlData = caseHtmlDataDao.findByLimit();
        PageInfo<CaseHtmlData> pageInfo = new PageInfo<>(htmlData);
        StringBuilder sb = new StringBuilder();
        PerceptronNERecognizer recognizer = new PerceptronNERecognizer(Config.Model.NER);
        Segment segment = HanLPFactory.builder().segment(true);
        for (CaseHtmlData caseHtmlData : pageInfo.getList())
        {
            String html = caseHtmlData.getHtml();
            if (StringUtils.isNotBlank(html))
            {
                CaseHtmlAnalysis analysis = caseHtmlAnalysisService.analysis(caseHtmlData, recognizer, segment);
                if (analysis != null
                        && (StringUtils.isBlank(analysis.getProsecutor()) && StringUtils.isBlank(analysis.getDefendant())))
                {
                    sb.append("docId: ").append(caseHtmlData.getDocId()).append("\n");
                    sb.append("上诉人: ").append(analysis.getProsecutor()).append("\n");
                    sb.append("被上诉人: ").append(analysis.getDefendant()).append("\n");
                    sb.append("上诉人代理律师: ").append(analysis.getProsecutorLawyer()).append("\n");
                    sb.append("被上诉人代理律师: ").append(analysis.getDefendantLawyer()).append("\n");
                    sb.append("****************************************************************").append("\n");
                    sb.append("\n");
                    sb.append("\n");
                    sb.append("\n");
                }
            }
        }
        IOUtil.saveTxt("C:\\Users\\Andy zhang\\Desktop\\analysis1.txt", sb.toString());
    }


    @Test
    public void analysisIncrementTest() {
        caseHtmlAnalysisService.analysisIncrement();
    }

    @Test
    public void insertOrUpdateAnalysisTest()
    {
        try {
            int pageNum=1, pageSize=1000;
            PageInfoResp pageInfoResp;
            while (true)
            {
                System.out.printf("pageNum: %d  pageSize: %d \n", pageNum, pageSize);
                pageInfoResp = caseHtmlAnalysisFacade.analysisIncrement(pageNum, pageSize);
                if (!pageInfoResp.isHasNextPage())
                {
                    break;
                }
                pageNum++ ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseDsrxxIncrementTest()
    {
        try {
            int pageNum=1, pageSize=1000;
            PageInfoResp pageInfoResp;
            while (true)
            {
                System.out.printf("pageNum: %d  pageSize: %d \n", pageNum, pageSize);
                pageInfoResp = caseHtmlDsrxxFacade.parseDsrxxIncrement(pageNum, pageSize);
                if (!pageInfoResp.isHasNextPage())
                {
                    break;
                }
                pageNum++ ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseDocJson4Test()
    {
//        String s = wenshuHtmlSaveUntil.parseDocJson4("<a type='dir' name='WBSB'></a><div style='TEXT-ALIGN: center; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 宋体; FONT-SIZE: 22pt;'>上海市第一中级人民法院</div><div style='TEXT-ALIGN: center; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 仿宋; FONT-SIZE: 26pt;'>民 事 判 决 书</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm;  FONT-FAMILY: 仿宋;FONT-SIZE: 16pt; '>（2016）沪01民终13566号</div><a type='dir' name='DSRXX'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>上诉人（原审被告）：翟楼，男，1979年8月10日出生，汉族，住河南省兰考县。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>委托诉讼代理人：郭小平，北京安博（上海）律师事务所律师。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>被上诉人（原审原告）：江苏神龙海洋工程有限公司，住所地江苏省靖江市人民南路28号。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>法定代表人：张桂清，董事长。</div><a type='dir' name='SSJL'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>委托诉讼代理人：阮红斌，上海市海华永泰律师事务所律师。上诉人翟楼因与被上诉人江苏神龙海洋工程有限公司（以下简称神龙公司）确认劳动关系纠纷一案，不服上海市松江区人民法院（2016）沪0117民初14785号民事判决，向本院提起上诉。本院于2016年12月2日立案后，依法组成合议庭，公开开庭进行了审理。上诉人翟楼的委托诉讼代理人郭小平、被上诉人神龙公司的委托诉讼代理人阮红斌到庭参加诉讼。本案现已审理终结。</div><a type='dir' name='AJJBQK'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>翟楼上诉请求：撤销原审判决，依法改判确认双方2015年12月17日至2016年1月14日期间不存在劳动关系。事实和理由：其由案外人叶某安排工资并支付报酬，但叶某、杜某系公司的工地管理人员。翟楼戴有公司发放的安全帽，按规定劳动者持有工作证和服务证即能证明劳动关系，给劳动者造成损害的，承担连带责任。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>神龙公司辩称，原审在2016年8月、9月，翟楼在同年1月受伤就不在工地了。翟楼由叶某招工、管理、发薪。故不同意上诉请求，认为原审判决正确，要求维持原判。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>神龙公司向原审法院起诉请求：确认双方2015年12月17日至2016年1月14日期间不存在劳动关系。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>原审法院认定事实：神龙公司承建了位于上海市松江区XX镇XX公路XX号上海XX有限公司扩建生产及辅助用房工程。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>原审审理中，翟楼称：2015年12月17日，翟楼由叶某招聘至沈砖公路嘉莱顿工地从事泥工工作，口头约定每天工资220元，日常工作由叶某安排管理，工资由叶某发放，每天以现金方式结清。2016年1月14日，翟楼在工作时被车牌号为湘AXXX**的混凝土泵车的泵管打到受伤，之后未再至工地工作。神龙公司称，该工地的泥工的所有工作量均清包给了案外人杜某，神龙公司并未雇佣翟楼，杜某、叶某均非神龙公司的员工。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>原审法院认为，本案争议的焦点是双方之间是否存在劳动关系。劳动关系是劳动者与用人单位之间以劳动力为对价的财产关系，同时还兼具劳动者对用人单位的人身依附关系。翟楼确认其系叶某招聘至工地工作，接受叶某的工作安排，劳动报酬也由叶某结算，翟楼亦无证据证明叶某系代表神龙公司招用翟楼工作。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>综上所述，根据本案现有证据，尚不足以证明双方之间符合劳动关系的特征，神龙公司要求确认与翟楼之间不存在劳动关系的诉讼请求，于法有据，原审法院予以支持。据此，依照《中华人民共和国劳动合同法》第二条第一款规定，原审法院于二Ｏ一六年十月二十四日作出判决：江苏神龙海洋工程有限公司与翟楼2015年12月17日至2016年1月14日期间不存在劳动关系。案件受理费10元，减半收取5元，由江苏神龙海洋工程有限公司负担。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本院二审期间中,翟楼围绕上诉请求提交了证据。本院组织当事人进行证据交换和质证。翟楼提交证据如下：工程概况、告示牌和翟楼戴安全帽的照片，旨在证明其是公司的员工。神龙公司表示，对此真实性不认可，不能证明叶某系公司员工，翟楼是叶某招进来的，亦不能证明拍摄的时间、地点，不能证明翟楼受神龙公司招录和管理。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本院认为，经查，工程概况和告示牌上均未显示翟楼的招用和发薪人员叶某的姓名，杜某又非翟楼的招用和发薪人员，翟楼在原审中也明确表示不认识杜某。翟楼戴有安全帽的照片据其称在原审时拍摄，但未有证据证明其拍摄的时间、地点以及安全帽系神龙公司发放。因此，翟楼提供的上述证据不能佐证其的陈述，故本院对其证明力不予认定。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二审中，神龙公司没有提交新证据。本院经审理查明，原审认定事实正确。本院依法予以确认。</div><a type='dir' name='CPYZ'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本院认为，本院认为，本案争议焦点为翟楼与神龙公司之间是否存在劳动关系。依照《最高人民法院关于适用的解释》第九十一条之规定，主张法律关系存在的当事人，应当对产生该法律关系的基本事实承担举证证明责任。本案中，翟楼确认其系叶某招聘至工地工作，接受叶某的工作安排，劳动报酬也由叶某结算，翟楼亦无证据证明叶某系代表神龙公司招用翟楼的。翟楼主张其与神龙公司之间存在劳动关系，神龙公司予以否认。对此，翟楼提供了上海市公安局案（事）件接报回执单、叶某出具的证明两份、叶某的施工记录复印件一份、工程概况、告示牌、翟楼戴安全帽的照片等证据予以证明，但是上述证据尚不足以证明翟楼的事实主张，故翟楼要求确认其与神龙公司之间存在劳动关系的上诉请求，依据不足，本院不予支持。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>综上所述，翟楼的上诉请求均不能成立，应予驳回；原审判决认定事实清楚，适用法律正确，应予维持。依照《中华人民共和国民事诉讼法》第一百七十条第一款第（一）项规定，判决如下：</div><a type='dir' name='PJJG'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>驳回上诉，维持原判。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二审案件受理费人民币10元，由上诉人翟楼负担。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本判决为终审判决。</div><a type='dir' name='WBWB'></a><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审判长　　王剑平</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审判员　　顾慧萍</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审判员　　李伟林</div><br/><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二〇一七年三月一日</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>书记员　　强　斐</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>附：相关法律条文</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>《中华人民共和国民事诉讼法》第一百七十条第二审人民法院对上诉案件，经过审理，按照下列情形，分别处理：（一）原判决、裁定认定事实清楚，适用法律正确的，以判决、裁定方式驳回上诉，维持原判决、裁定；……</div>");
        Segment segment = HanLPFactory.builder().segment(true);
        String s = wenshuHtmlSaveUntil.parseDocJson5("<a type='dir' name='WBSB'></a><div style='TEXT-ALIGN: center; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 宋体; FONT-SIZE: 22pt;'>浙江省金华市婺城区人民法院</div><div style='TEXT-ALIGN: center; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 仿宋; FONT-SIZE: 26pt;'>民 事 裁 定 书</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm;  FONT-FAMILY: 仿宋;FONT-SIZE: 16pt; '>（2017）浙0702民申34号</div><a type='dir' name='DSRXX'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>再审申请人（一审被告）：包援东，女，汉族，1971年2月22日出生，住金华市婺城区。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>被申请人（一审原告）：吴玉卫，女，汉族，1983年2月12日出生，住金华市婺城区。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>被申请人（一审被告）：徐坚勇，男，汉族，1970年11月25日出生，住金华市婺城区。</div><a type='dir' name='SSJL'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>再审申请人包援东因与被申请人吴玉卫、徐坚勇民间借贷纠纷一案，不服本院（2011）金婺商初字第1054号民事判决，向本院申请再审。本院依法组成合议庭进行了审查，现已审查终结。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>包援东申请再审称：一、根据《民事诉讼法》第二百条第二项的规定，原审判决认定的基本事实错误，缺乏证据证明。原审法院认为，本案债务发生在再审申请人与被申请人徐坚勇婚姻关系存续期间，尚无充分证据证明本案借款用于（被申请人徐坚勇）赌博，应当按夫妻共同债务处理。对此，再审申请人认为，根据其在本案诉讼中提供的证据以及另案判决的结果，本案借款应当属于被申请人徐坚勇的个人债务，与再审申请人无关，原审判决将其认定为夫妻共同债务，进而判决由再审申请人就该债务承担连带清偿责任，显属错误。1.根据被申请人吴玉卫的陈述，涉案借款发生在2009年至2010年2月之间，且涉及多次借款，在2010年5月5日经结算尚欠借款本金22万元。被申请人吴玉卫借给被申请人徐坚勇至少22万元的借款，借款数额明显超出夫妻日常生活所需，故被申请人吴玉卫应对借款是否系再审申请人和被申请人徐坚勇夫妻的共同意思表示或借款是否用于夫妻共同生活负有合理的注意义务和相应的举证责任。但被申请人吴玉卫在本案中却并未举证证明涉案借款用于再审申请人与被申请人徐坚勇的家庭共同生活，也未举证证明其已经尽到善意和无过失的注意义务。2.从本案各方当事人的关系上看，再审申请人系被申请人吴玉卫丈夫的姨妈，即也是亲戚关系。被申请人吴玉卫与再审申请人夫妻较为熟悉，在曾有资金往来的情况下，被申请人吴玉卫完全可以要求再审申请人夫妻双方就借款作出共同的意思表示。但被申请人吴玉卫却未举证证明其在借款发生期间内有将借款情况告知过再审申请人，或者再审申请人在被申请人徐坚勇借款后对债务进行了追认。3.被申请人徐坚勇具有赌博恶习，涉案借款实际用于其个人赌博及挥霍。根据再审申请人在本案诉讼中提供的公安行政处罚决定和有关村民委员会出具的证明等证据显示，被申请人徐坚勇曾因赌博于2006年8月15日被金华市公安局婺城分局刑事侦查大队情报中队查获，于2007年11月29日被金华市公安局婺城分局给予行政处罚，即存在赌博恶习，并长期沉迷赌博。4.涉案借款虽然发生在再审申请人与被申请人徐坚勇婚姻关系存续期间，但双方在涉案借款发生前后并未添置重大家庭财产。而再审申请人和被申请人徐坚勇于2010年8月20日即已登记离婚，此时距借条出具之日仅有三个月时间，被申请人吴玉卫也应对再审申请人夫妻关系不和的事实有所了解，且离婚协议书中明确约定除向长山乡信用社贷款的8万元之外，无其他共同债务。故涉案借款不宜认定为因夫妻日常生活所需的共同债务，而应认定为是被申请人徐坚勇的个人债务。6.金华市婺城区人民法院于同日（2011年10月9日）受理的另一起民间借贷纠纷案【（2011）金婺商初字第1049号】中，被告同样是再审申请人和徐坚勇，原告据以提起诉讼的事实和理由与本案相同，但该案最终的二审生效判决却认定涉案债务为被申请人徐坚勇的个人债务，再审申请人无需承担清偿责任。在该案中，一审法院认定涉案债务属于夫妻共同债务，同样判决由被申请人徐坚勇承担直接偿还责任，而由再审申请人承担连带清偿责任。后再审申请人不服提起上诉，该案经金华市中级人民法院审理，作出（2012）浙金商终字第457号民事判决书，认定出借人在未举证证明其已尽善意和无过失注意义务，涉案款项已用于家庭共同生活、经营的情况下，结合徐坚勇存在赌博恶习，涉案借款应认定为徐坚勇的个人债务。故二审法院依法撤销了原审判决，驳回出借人要求再审申请人承担清偿责任的诉讼请求。因此，本案被申请人吴玉卫与徐坚勇之间的借款，系被申请人徐坚勇的个人债务，再审申请人无需承担清偿责任。二、根据《民事诉讼法》第二百条第六项的规定，原审判决适用法律错误。本案借款虽然发生在再审申请人与被申请人徐坚勇婚姻关系存续期间，但再审申请人对该借款并不知情，并提供了所在村民委员会出具的证明和公安机关行政处罚决定书等证据证明被申请人徐坚勇存在赌博恶习。而被申请人徐坚勇先后多次向被申请人吴玉卫借款至少22万元，该借款数额较大已超出用于家庭日常生活的范畴。更何况，被申请人吴玉卫也未举证证明该借款的具体用途以及已经将借款情况告知过再审申请人的情形。故对于被申请人吴玉卫而言，其根本没有理由相信本案的借款系再审申请人与被申请人徐坚勇的共同意思表示。在此情形下，原审法院直接适用最高人民法院《关于适用&lt;中华人民共和国婚姻法&gt;若干问题的解释（二）》第二十四条的规定，推定本案债务属于夫妻共同债务，属适用法律错误。综上，本案原审判决认定的基本事实缺乏证据证明，且适用法律错误，故依法申请再审，请求撤销原判决。</div><a type='dir' name='CPYZ'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本院经审查认为:1.包援东在再审审查过程中提供的浙江省金华市中级人民法院（2012）浙金商终字第457号民事判决书，不属于再审新证据的范畴，且无法推翻原审判决，本院不予采纳。2.包援东于2017年12月向法院提出再审申请，已超过六个月的法定期限。综上，包援东的再审申请不符合《中华人民共和国民事诉讼法》第二百条第一、二项规定的情形，依法应予驳回。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>依照《中华人民共和国民事诉讼法》第二百零四条第一款、第二百零五条，《最高人民法院关于适用&lt;中华人民共和国民事诉讼法&gt;的解释》第三百九十五条第二款规定，裁定如下：</div><a type='dir' name='PJJG'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>驳回包援东的再审申请。</div><a type='dir' name='WBWB'></a><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审 判 长　郑向华</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审 判 员　刘爱军</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审 判 员　薛焕梅</div><br><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二〇一八年二月五日</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>代书记员　章　琴</div>", segment);
        System.out.println(s);
    }

    @Test
    public void analysisTest() throws Exception
    {
        CaseHtmlData object = new CaseHtmlData();
        object.setDocId("fa734829-9f90-4cdd-9b5c-a7c60021445e");
        object.setHtml("<div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'><htmlxmlns:o='urn:schemas-microsoft-com:office:office'xmlns:w='urn:schemas-microsoft-com:office:word'xmlns:m='http://schemas.microsoft.com/office/2004/12/omml'xmlns='http://www.w3.org/TR/REC-html40'><head><metahttp-equiv=Content-Typecontent='text/html;charset=GBK'/><style>.MsoNormal{margin-top:0cm;margin-bottom:0px}</style></head><bodylang=ZH-CNstyle='tab-interval:36.0pt;text-justify-trim:punctuation'></div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'><pstyle=”TEXT-ALIGN:center;LINE-HEIGHT:22.5pt”class=”MsoNormal”align=”center”><spanstyle=”FONT-FAMILY:仿宋;COLOR:#333333;FONT-SIZE:16pt”lang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:center;TEXT-INDENT:-5.25pt;MARGIN-LEFT:13.25pt”class=”MsoNormal”align=”center”><spanlang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:center;TEXT-INDENT:-16pt;MARGIN-LEFT:24pt”class=”MsoNormal”align=”center”><strong><spanstyle=”FONT-FAMILY:方正小标宋简体;LETTER-SPACING:3pt;FONT-SIZE:26pt”>呼和浩特市新城区人民法院</span></strong></p><pstyle=”TEXT-ALIGN:center”class=”MsoNormal”align=”center”><strong><spanstyle=”FONT-FAMILY:方正小标宋简体;FONT-SIZE:26pt”>民事判决书</span></strong></p><pclass=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:方正小标宋简体;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”TEXT-ALIGN:right;WORD-BREAK:break-all”class=”MsoNormal”align=”right”><spanlang=”EN-US”>（</span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”>2018）</span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>内<spanlang=”EN-US”>0102</span>民初<spanlang=”EN-US”>4785</span>号<spanlang=”EN-US”></span></span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>原告：内蒙古帝都房地产开发有限公司，住所地内蒙古自治区呼和浩特市赛罕区巴彦镇黑土凹村。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>法定代表人：连惟，该公司总经理。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>委托诉讼代理人：善文哲，内蒙古汇孚律师事务所律师。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>委托诉讼代理人：丁志彬，内蒙古汇孚律师事务所律师。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>被告：李剑璋</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>委托诉讼代理人：王晓革，内蒙古东日律师事务所律师。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>委托诉讼代理人：李静娴，内蒙古东日律师事务所律师。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>原告内蒙古帝都房地产开发有限公司与被告李剑璋确认合同无效纠纷一案。本院于<spanlang=”EN-US”>2018</span>年<spanlang=”EN-US”>8</span>月<spanlang=”EN-US”>10</span>日立案后，依法适用简易程序，公开开庭进行了审理。原告内蒙古帝都房地产开发有限公司的委托诉讼代理人丁志彬，被告李剑璋及其委托诉讼代理人王晓革到庭参加诉讼。本案现已审理终结。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>原告内蒙古帝都房地产开发有限公司向本院提出诉讼请求：<spanlang=”EN-US”>1.</span>请求贵院依法判决原告与被告<spanlang=”EN-US”>2015</span>年<spanlang=”EN-US”>7</span>月<spanlang=”EN-US”>8</span>日签订的《商品房认购合同书》无效；<spanlang=”EN-US”>2.</span>依法判令被告承担本案诉讼费用。事实和理由：<spanlang=”EN-US”>2015</span>年<spanlang=”EN-US”>7</span>月<spanlang=”EN-US”>8</span>日，原告与被告签订了《商品房认购合同书》，合同约定，被告认购原告位于陶然雅居小区第<spanlang=”EN-US”>C8</span>号楼<spanlang=”EN-US”>3</span>单元<spanlang=”EN-US”>2</span>层东号房屋一套。由于该合同并非双方的真实意思表示，该合同没有实际履行，双方并未发生真实的买卖关系<spanlang=”EN-US”>,</span>原、被告签订的《商品房认购合同书》无效。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>被告李剑璋辩称：一、原、被告之间具有合法有效的房屋买卖合同关系。首先，原、被告签订的《商品房认购合同书》（以下简称合同）中有原、被告在合同中签字、盖章确认，系双方真实的意思表示，不存在无效、可撤销的情形，原、被告之间存在合法有效的房屋买卖合同关系，应当按合同约定全面履行自身义务。其次原告在与被告签订合同后，又向被告开具《收据》载明已经收到购房款，恰恰可以证明原告与被告签订的合同是其真实的意思表示。综上<spanlang=”EN-US”>,</span>原、被告存在房屋买卖合同法律关系，不存在《中华人民共和国合同法》第五十二条规定的合同无效情形，原告以并非其真实意思表示否认合同效力显然不能成立。二、被告已通过债权转让的方式向原告支付购房款。本案中被告向原告支付购房款的方式是通过债权债务转让方式实现，原告与案外人杨贵亮之间存在欠付工程款的债权债务法律关系，案外人杨某与杨贵亮因钢窗买卖合同存在债权债务法律关系，被告与案外人杨某之间因居间合同存在债权债务法律关系，上述事实在庭审中有原、被告陈述及《居间合同》相印证。本案中案外人杨贵亮将对原告的债权转让给被告，根据《中华人民共和国合同法》第八十条规定：“债权人转让权利的，应当通知债务人。未经通知，该转让对债务人不发生效力。债权人转让权利的通知不得撤销，但经受让人同意的除外。”原告通过签订合同及出具收据的方式认可已经收到债权转让的通知，债权转让行为已完成且不可撤销，被告已通过债权转让的方式向原告支付完全部购房款。显然原告庭审主张的未经其同意该债权转让行为不发生效力的说法与其签订合同与开具收据的行为相矛盾。三、原告主张不具有房屋预售许可证，故合同无效的说法违反诚实信用原则不应得到支持。原告庭审中以《最高人民法院关于审理商品房买卖合同纠纷案件适用法律若干问题的解释》第二条规定：“出卖人未取得商品房预售许可证明，与买受人订立的商品房预售合同，应当认定无效，但是在起诉前取得商品房预售许可证明的，可以认定有效。”主张合同无效的说法不应得到支持，理由如下：首先从该司法解释起草的目的在于规范开发商存在的如无证销售、一房数卖等扰乱房地产巿场秩序的行为，目的在于保护买房人的利益。本案中原告明知涉案房屋缺乏相应开发手续，不仅不积极完善，还以此为借口请求确认合同无效，企图从其不法行为中获取利益，该抗辩理由不仅使被告订立房屋买卖合同目的落空，破坏交易安全，原告的行为严重地违背了诚实信用原则。其次因涉案房屋已是现房，业主们正在陆续收房，若釆纳原告观点势必使所有业主的房屋买卖合同处于不稳定状态，不仅不利于交易安全，违背社会公共利益，甚至会使原告从此不法行为中获取利益，制约购房者主张。更有甚者，如房地产开发企业以此为例，蜂拥而起，将会对房屋买卖合同的稳定性产生恶劣的影响，损害公共利益。综上，原告以其未办理房屋预售许可为由请求确认合同无效严重违反诚实信用原则，其请求确认合同无效的主张不应得到支持。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>当事人围绕诉讼请求依法提交了证据，本院组织当事人进行了证据交换和质证。对当事人无异议的证据，本院予以确认并在卷佐证。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”>2015</span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>年<spanlang=”EN-US”>7</span>月<spanlang=”EN-US”>8</span>日，原告与被告签订《商品房认购合同书》，约定原告购买被告开发的位于呼和浩特市××区第<spanlang=”EN-US”>C8</span>号楼<spanlang=”EN-US”>3</span>单元<spanlang=”EN-US”>2</span>层东号房屋，建筑面积<spanlang=”EN-US”>84.2</span>平方米，购房款为<spanlang=”EN-US”>294700</span>元。同日，原告为被告开具收据，载明收到被告购房款<spanlang=”EN-US”>294700</span>元。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>本院认为，原告与被告自愿签订《商品房认购合同书》，其内容亦不违反法律、行政法规的禁止性规定，合同依法成立，合法有效，双方均应恪守。《最高人民法院关于审理商品房买卖合同纠纷案件适用法律若干问题的解释》</span><spanlang=”EN-US”><spanstyle=”FONT-FAMILY:仿宋;COLOR:black;FONT-SIZE:16pt”lang=”EN-US”><spanlang=”EN-US”>第二条</span></span></span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>规定，</span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>出卖人未取得商品房预售许可证明，与买受人订立的商品房预售合同，应当认定无效，但是在起诉前取得商品房预售许可证明的，可以认定有效。本司法解释的目的在于规范房地产市场，惩治无证销售的行为，维护房地产交易秩序，保护广大购房人的合法权益。民事活动应当遵循诚信原则，秉持诚实，恪守承诺。本案中，原告据以上述司法解释条文主张</span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>《商品房认购合同书》</span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>无效，使购房人的商品房买卖合同目的落空，严重违法诚信原则。对于原告的诉讼请求，违反民法的基本原则，故本院不予支持。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>依照《中华人民共和国民法总则》第七条，《</span><spanlang=”EN-US”><spanstyle=”FONT-FAMILY:仿宋;COLOR:windowtext;FONT-SIZE:16pt”lang=”EN-US”><spanlang=”EN-US”>中华人民共和国合同法</span></span></span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>》</span><spanlang=”EN-US”><spanstyle=”FONT-FAMILY:仿宋;COLOR:windowtext;FONT-SIZE:16pt”lang=”EN-US”><spanlang=”EN-US”>第六十条</span></span></span><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>规定，判决如下：</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>驳回原告内蒙古帝都房地产开发有限公司的诉讼请求。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>案件受理费减半收取<spanlang=”EN-US”>100</span>元，由原告内蒙古帝都房地产开发有限公司负担。</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>如不服本判决，可在判决书送达之日起十五日内向本院递交上诉状，并按对方当事人的人数提出副本，上诉于呼和浩特市中级人民法院。</span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char;MARGIN-RIGHT:16pt”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char;MARGIN-RIGHT:16pt”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>审判员<spanlang=”EN-US”></span>李福林</span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char;MARGIN-RIGHT:16pt”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>二〇一八年十月十六日</span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char;MARGIN-RIGHT:16pt”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”>书<spanlang=”EN-US”></span>记员<spanlang=”EN-US”></span>刘<spanlang=”EN-US”></span>芸</span></p><pstyle=”LINE-HEIGHT:26pt;TEXT-INDENT:32pt;LAYOUT-GRID-MODE:char”class=”MsoNormal”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”></span></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:26pt;TEXT-INDENT:32pt;WORD-BREAK:break-all”class=”MsoNormal”align=”right”><spanstyle=”FONT-FAMILY:仿宋;FONT-SIZE:16pt”lang=”EN-US”></span></p></body></html></div>");
        PerceptronNERecognizer recognizer = new PerceptronNERecognizer(Config.Model.NER);
        Segment segment = HanLPFactory.builder().segment(true);
        CaseHtmlAnalysis analysis = caseHtmlAnalysisService.analysis(object, recognizer,segment);
        if (analysis != null)
        {
//            List<CaseHtmlAnalysis> list = new LinkedList<>();
//            list.add(analysis);
//            caseHtmlAnalysisDao.insertOrUpdateAnalysisList(list);
            System.out.println(analysis.toString());
        }
    }

    @Test
    public void analysisHasUpdateTest()
    {

        try {
            int pageNum=1, pageSize=1000;
            PageInfo<CaseHtmlData> pageInfo;
            PerceptronNERecognizer recognizer = new PerceptronNERecognizer(Config.Model.NER);
            Segment segment = HanLPFactory.builder().segment(true);
            List<CaseHtmlAnalysis> list = new LinkedList<>();
            while (true) {
                System.out.printf("pageNum: %d  pageSize: %d \n", pageNum, pageSize);
                PageHelper.startPage(pageNum, pageSize);
//                List<CaseHtmlData> exist = caseHtmlAnalysisDao.findByEarlyTime("2019-04-03 16:35:16");
                List<CaseHtmlData> exist = caseHtmlAnalysisDao.findByEarlyTime("2019-04-03 19:15:00");
                pageInfo = new PageInfo<>(exist);
                for (CaseHtmlData caseHtmlData : exist) {
                    CaseHtmlAnalysis analysis = caseHtmlAnalysisService.analysis(caseHtmlData, recognizer,segment);
                    if (analysis != null) {
                        caseHtmlAnalysisDao.insertOrUpdateAnalysis(analysis);
                    }
//                    if (analysis != null)
//                    {
//                        list.add(analysis);
//                    }
//
//                    if (list.size() >= 500)
//                    {
//                        try
//                        {
//                            caseHtmlAnalysisDao.insertOrUpdateAnalysis(list);
//                        } catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//
//                        list.clear();
//                    }
//                }
//                if (list.size() > 0)
//                {
//                    try
//                    {
//                        caseHtmlAnalysisDao.insertOrUpdateAnalysis(list);
//                    } catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
                }
                    if (!pageInfo.isHasNextPage()) {
                        break;
                    }
                    pageNum++;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void analysisFullTest()
    {
        caseHtmlAnalysisService.analysisFull();
    }

    @Test
    public void segTest()
    {
        Segment segment = HanLPFactory.builder().segment(true);
        List<Term> seg = segment.seg("原告所支出的租金和装修费是其必须支出的经营成本，不属于损失");
        System.out.println(seg.toString());
    }

}
