package com.tzCloud.test;

import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.core.facade.legalEngine.LawyerSqlDataInitializeFacade;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.service.CaseHtmlDsrxxService;
import com.tzCloud.core.service.DsrxxParseStatusService;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaolei
 * @create 2019-04-24 16:10
 */
public class LawyerSqlDataInitTest extends TestBase {

    @Autowired
    private LawyerSqlDataInitializeFacade lawyerSqlDataInitializeFacade;
    @Autowired
    private DsrxxParseStatusService dsrxxParseStatusService;
    @Autowired
    private CaseHtmlDsrxxService caseHtmlDsrxxService;

    @Test
    public void caseHtmlDsrxxInitTest() {
        lawyerSqlDataInitializeFacade.caseHtmlDsrxxInit();
    }

    @Test
    public void initDsrxxParseStatusData() {
        dsrxxParseStatusService.initDsrxxParseStatusData();
    }

    @Test
    public void updateDsrxxParseStatusData() {
        dsrxxParseStatusService.updateDsrxxParseStatusData();
    }

    @Test
    public void parseDsrxxIncrementNewTest() {
        caseHtmlDsrxxService.parseDsrxxIncrementNew();
    }

    @Test
    public void parseDsrxxUpdateTest() {
        caseHtmlDsrxxService.parseDsrxxUpdate();
    }

    @Test
    public void lawyerDataIdentityInitTest() {
        lawyerSqlDataInitializeFacade.lawyerDataIdentityInit();
    }

    @Test
    public void lawyerDataWinFlagInitTest() {
        lawyerSqlDataInitializeFacade.lawyerDataWinFlagInit();
    }

    @Test
    public void parseLawyerInfoInitTest() {
        lawyerSqlDataInitializeFacade.parseLawyerInfoInit();
    }

    @Test
    public void parseDocJson5Test(){
        Segment segment = HanLPFactory.builder().segment(true);
        String s = WenshuHtmlSaveUntil.parseDocJson5("<div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'><p></p></div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'><title></title></div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'><p></p></div><a type='dir' name='WBSB'></a><div style='TEXT-ALIGN: center; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 宋体; FONT-SIZE: 22pt;'>广西壮族自治区贵港市中级人民法院</div><div style='TEXT-ALIGN: center; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm; FONT-FAMILY: 仿宋; FONT-SIZE: 26pt;'>民 事 判 决 书</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 30pt; MARGIN: 0.5pt 0cm;  FONT-FAMILY: 仿宋;FONT-SIZE: 16pt; '>（2018）桂08民终1385号</div><a type='dir' name='DSRXX'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>上诉人（原审被告、反诉原告）：李泽文，男，1968年7月9日出生，汉族，住广西贵港市港南区。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>被上诉人（原审原告、反诉被告）：雷承铙，男，1982年6月5日出生，汉族，住广西横县。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>委托诉讼代理人：唐淳艳，广西广为律师事务所律师。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>原审第三人：吴霜，女，1977年7月18日出生，汉族，住广西贵港市港南区。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>原审第三人：吴达良，男，1964年10月3日出生，汉族，住贵港市港南区。</div><a type='dir' name='SSJL'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>上诉人李泽文因与被上诉人雷承铙，原审第三人吴霜、吴达良买卖合同纠纷一案，不服贵港市港南区人民法院（2017）0803民初1411号民事判决，向本院提出上诉。本院于2018年8月24日立案受理后，依法组成合议庭进行了审理。本案现已审理终结。</div><a type='dir' name='AJJBQK'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>李泽文上诉请求：撤销原判第二、第三、第五项，改判被上诉人赔偿上诉人经济损失56000元；一、二审受理费由被上诉人负担。事实与理由：一、原判认定事实错误。1.上诉人与第三人没有纠纷，完全因为被上诉人没有依约付款所致。退一步讲，即使有纠纷，根据合同相对性原则，也与被上诉人无关；2.被上诉人砍树受阻，但在其开工第三天，木梓司法所已经调解解决了纠纷，上诉人要求被上诉人支付第二期货款，但被上诉人没有支付，构成违约；3.本案林木不存在争议，道路也不受阻，上诉人无需赔偿被上诉人损失；4、上诉人收取被上诉人的39900元实际上是办理采伐证及修路费用，应由被上诉人负担；5.由于被上诉人没有依约支付第二期林木款，导致合同解除，造成上诉人损失56000元，被上诉人应予赔偿。二、原判没有依法提取被上诉人手机上聊天记录，属于程序违法。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>雷承铙辩称，原判认定事实清楚，处理恰当，上诉人上诉无理，请求驳回上诉，维持原判。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>原审第三人吴霜、吴达良未作陈述。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>雷承铙向一审法院提出诉讼请求：1.确认原告雷承铙与被告李泽文签订的《林木买卖合同》于2017年9月19日解除；2.被告李泽文双倍返还原告雷承铙定金60000元；3.被告李泽文返还原告雷承铙已支付的款项39900元；4.被告李泽文承担原告雷承铙组织工人进场采伐、运输支出的劳务费3400元；5.本诉的诉讼费由被告李泽文承担。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>李泽文向一审法院提出反诉请求：1.反诉被告雷承铙赔偿经济损失56000元给反诉原告李泽文；2.反诉诉讼费由反诉被告雷承铙承担。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>一审法院认定事实：雷承铙为购买速生桉原木采伐，与李泽文达成买卖协议，并于2017年5月27日向李泽文支付定金30000元。2017年6月1日，李泽文（甲方）与雷承铙（乙方）正式签订《林木买卖合同》一份，合同约定：甲方将自己承包的木梓镇大兴村二队狗石岭所种的速生桉原木卖给乙方，必须保证卖给乙方的林木界线清楚无争议，林地林木权属无纠纷；出卖的原木只指甲方所承包的地域范围内，林地面积约160亩；成交金额为635000元，第一期双方签合同时付定金30000元，定于2017年5月27日交付；第二期在办理取得林木采伐证后，工人进场开工后两天内交付200000元；第三期于林木采伐至三分之一后支付350000元；余款55000元于工地完工前五日付清；办理采伐证等税费由乙方负责；砍伐时间定于2017年9月底前完工，如遇天气等不可抗力的因素影响可适当延长砍伐时间，双方再友好协商议定；甲方负责疏通林区道路的开通事宜，如需经过其它村民或单位的林地以及农作物的需要补偿相关费用的由甲方负责；林区道路至乡镇级公路之间所经过的其他单位或个人的道路范围甲方负责协商疏通，如有其他单位或个人提出需要收取过路费用的由甲方负责，与乙方无关；双方现场确认界线后，乙方应按约定的时间、付款方式支付应交的款项；乙方按约定的时间砍伐，如乙方在未受到其他因素的影响下逾期未安排工人进行采伐的，甲方有权单方终止履行合同，定金不予退回；乙方在采伐进程中如遇到有其他村民或集体提出林地权属存在争议和纠纷的以及林区道路至乡镇级公路之间的道路受阻的，甲方三天内沟通处理完毕，不影响乙方采伐进度和经济损失；甲方如多次疏通处理无果的，造成乙方无法进行后续采伐工作完成的，甲方必须赔偿乙方损失等内容。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>合同签订后，雷承铙通过银行转账的方式分别于2017年6月2日、7月11日向李泽文支付款项30000元、9900元，合计39900元。李泽文亦向雷承铙交付港南区采字[2017]0526013号、[2017]0526014、[2017]0526015《林木采伐许可证》。2017年9月14日，雷承铙组织梁老柱等数名工人进场搭建工棚，并于9月15日上午开始采伐树木，当天下午，第三人吴霜的弟弟到场以超过砍伐时期为由，阻止工人继续伐木，工人即停止砍伐。2017年9月16日下午，第三人吴达良到场以未支付林木款为由阻止工人运输已砍伐的木材。为此，雷承铙停止砍伐。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>2017年9月18日,李泽文通过短信要求雷承铙解散工人，由李泽文安排自己的工人采伐林木。同日，雷承铙向梁老柱支付劳务款3400元，解散工人。同月19日下午，李泽文以雷承铙未依约履行第二期付款义务，已构成根本违约为由，通过微信向雷承铙送达《解除合同通知书》，该通知书载明：解除双方签订的《林木买卖合同》，我方（李泽文）收取的定金不予退回等内容。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>2017年9月20日，李泽文向吴达良支付超期不砍伐桉树的违约金45000元，并于2017年9月25日自行组织工人进场采伐林木，后将所伐木材与雷承铙进场所伐木材进行售卖。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>另查明，2017年5月10日，第三人吴霜将其在贵港市港南区木梓镇大兴村2队狗石岭种植的桉树以500000元的价格转让给第三人吴达良砍伐，双方签订《买卖林木合同书》一份，该合同约定：第三人吴霜将种植的桉树约137亩卖给第三人吴达良，砍伐时间三个月即领取采伐证之日起计算等内容。同月25日，吴达良将上述吴霜种植的桉树以605000元的价格转让给李泽文，双方签订《桉树转让买卖合同》一份，该合同约定：吴达良将自己承包的木梓镇大兴村2队狗石岭所种的速生桉原木卖给李泽文；出卖的原木只指吴达良所承包的地域范围内，林地面积约139亩；双方在签订合同时付定金60000元；第二期200000元在办理取得林木采伐证后，工人进场开工后当天内交付；第三期300000元在林木采伐至一半后支付；余款45000元在全部完工三天前全部付清；砍伐时间定于林业部门发证之日起至采伐有效期内四个月期限；如遇天气等不可抗力的因素影响，可适当延长砍伐时间；注：李泽文必须在领到采伐证之日起三个月内砍伐完毕等内容。后吴霜与吴达良因砍伐期限超期发生纠纷，并于2017年9月16日在贵港市港南区司法局木梓司法所的调解下，双方达成协议：吴达良砍伐时间二个月即2017年9月17日至2017年11月17日，吴达良另支付吴霜10000元。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>再查明，贵港市港南区林业局于2017年5月26日分别向吴霜颁发港南区采字[2017]0526013号、[2017]0526014号、[2017]0526015号《林木采伐许可证》，分别批准在小班（采伐四至：东至小班界，南至桉成，西至桉成，北至山顶）、58-1小班（采伐四至：东至冲沟，南至小班界，西至山顶，北至荒山）、70-1小班（采伐四至：东至荒山，南至山顶，西至山脊，北至小班界）采伐巨尾桉，采伐面积分别为0.13公顷、0.5公顷、7.93公顷，采伐期限均从2017年5月26日起至2017年11月30日止。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>原判认为，雷承铙与李泽文签订的《林木买卖合同》是双方当事人的真实意思表示，应认定合同有效。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>合同签订后，雷承铙组织工人砍伐过程中因遭受第三人阻止而停止砍伐，而后雷承铙、李泽文均同意《林木买卖合同》于2017年9月19日解除。雷承铙遭到吴霜、吴达良的阻挠，是由于李泽文未依约履行其与吴达良签订的《桉树转让买卖合同》，应认定《林木买卖合同》的解除是由于李泽文违约导致合同无法履行，属于合同的法定解除。合同解除后，当事人可以要求恢复原状、采取其他补救措施，并有权要求赔偿损失。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>关于雷承铙向李泽文交付的定金30000元，根据《最高人民法院关于适用&lt;中华人民共和国担保法&gt;若干问题的解释》第一百二十条第一款“因当事人一方迟延履行或者其他违约行为，致使合同目的不能实现，可以适用定金罚则。但法律另有规定或者当事人另有约定的除外”的规定，因双方未另有约定，《林木买卖合同》系由于李泽文违约导致无法履行，因此，根据定金罚则，收受定金的一方即李泽文应向支付定金的一方即雷承铙双倍返还定金60000元。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>关于雷承铙向李泽文支付的39900元，因《林木买卖合同》约定李泽文负责疏通林区道路的开通事宜，故李泽文辩称雷承铙支付的39900元包含疏通林区道路的钩机作业费30150元，明显与合同的约定不符，不予采信；李泽文举证伐区设计服务费为9758元，即使该费用为依约应由雷承铙承担的办理采伐许可证的费用，但雷承铙在履行合同过程中仅砍伐小部分林木即受到阻挠，并未获益，李泽文在合同解除后自行砍伐林木并将雷承铙所伐木材一并售卖，是最终的受益者，根据公平原则，办理采伐许可证的费用应由李泽文承担。因此，李泽文应向雷承铙返还已付的款项39900元。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>关于砍伐工人劳务费3400元，李泽文双倍返还的定金足以弥补雷承铙的经济损失，因此，雷承铙主张李泽文承担砍伐工人劳务费3400元，不予支持。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>关于李泽文提起的反诉请求，雷承铙与李泽文签订的《林木买卖合同》明确约定李泽文负责疏通林区道路的开通事宜，因此，李泽文提出雷承铙赔偿疏通林区道路钩机作业费11000元，不符合合同约定，不予支持；李泽文从吴达良处受让涉案林木砍伐，但其将涉案林木转让雷承铙时约定的砍伐期限却超出其与吴达良的约定，显然，其向吴达良支付的超期不砍伐桉树违约金45000元，与雷承铙的砍伐行为不存在因果关系，况且该违约金是李泽文在向雷承铙发出《解除合同通知书》之后再向吴达良支付，后亦由李泽文自行组织工人砍伐林木，李泽文是延长砍伐期限的受益者，因此，李泽文要求雷承铙赔偿其向吴达良支付的违约金45000元，无事实及法律依据，亦不予支持。为此，应驳回李泽文要求雷承铙赔偿56000元的反诉请求。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>依照《中华人民共和国合同法》第八条、第四十四条、第九十四条第二项、第九十六条第一款、第九十七条、第一百一十五条,以及《最高人民法院关于适用&lt;中华人民共和国担保法&gt;若干问题的解释》第一百二十条第一款的规定，判决：一、原告（反诉被告）雷承铙与被告（反诉原告）李泽文于2017年6月1日签订的《林木买卖合同》于2017年9月19日解除；二、被告（反诉原告）李泽文应双倍返还原告（反诉被告）雷承铙定金60000元；三、被告（反诉原告）李泽文应返还原告（反诉被告）雷承铙39900元；四、驳回原告（反诉被告）雷承铙的其他诉讼请求；五、驳回反诉原告（被告）李泽文的全部诉讼请求。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二审期间，各方当事人没有提供新的证据。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>经二审审理查明，原判认定事实属实，本院予以确认。</div><a type='dir' name='CPYZ'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本院认为，原判对是谁违约导致合同解除的问题已经作了充分的论述，原判认定李泽文违约正确，应予维持。李泽文上诉认为雷承铙违约没有提供新的证据证实，不予采信；关于李泽文收取雷承铙39900元应否退回的问题，原判对此也作了充分而正确的论述，本院予以支持，在此不再赘述。李泽文认为不应退回这39900元的理由不成立，不予支持；关于李泽文反诉要求雷承铙赔偿损失56000元的问题，因为雷承铙并没有违约，李泽文的损失与雷承铙无关，不应由雷承铙赔偿。关于原审法院没有提取雷承铙手机短信记录的问题，因双方承认李泽文是通过短信提出解除合同，是否提取这些短信记录不影响对这一事实的认定，而且这些短信记录李泽文的手机也会有记录，因而不能认定是程序违法。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>综上所述，李泽文上诉无理，应予驳回。原判正确，应予维持。依照《中华人民共和国民事诉讼法》第一百七十条第一款第一项的规定，判决如下：</div><a type='dir' name='PJJG'></a><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>驳回上诉，维持原判。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二审案件受理费1749元，由上诉人李泽文负担。</div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>本判决为终审判决</div><a type='dir' name='WBWB'></a><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审判长　　刘立技</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审判员　　陆志然</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>审判员　　马荣兴</div><br><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>二〇一八年十一月十四日</div><div style='TEXT-ALIGN: right; LINE-HEIGHT: 25pt; MARGIN: 0.5pt 72pt 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'>书记员　　张　谦</div>",
                segment);
        System.out.printf(s);
}


    @Test
    public void segProcessWithoutRestMapTest(){
        Segment segment = HanLPFactory.builder().segment(true);
        String s = WenshuHtmlSaveUntil.parseDocJson5("<div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'><htmlxmlns:o='urn:schemas-microsoft-com:office:office'xmlns:w='urn:schemas-microsoft-com:office:word'xmlns:m='http://schemas.microsoft.com/office/2004/12/omml'xmlns='http://www.w3.org/TR/REC-html40'><head><metahttp-equiv=Content-Typecontent='text/html;charset=GBK'/><style>.MsoNormal{margin-top:0cm;margin-bottom:0px}</style></head><bodylang=ZH-CNstyle='tab-interval:36.0pt;text-justify-trim:punctuation'></div><div style='LINE-HEIGHT: 25pt;TEXT-ALIGN:justify;TEXT-JUSTIFY:inter-ideograph; TEXT-INDENT: 30pt; MARGIN: 0.5pt 0cm;FONT-FAMILY: 仿宋; FONT-SIZE: 16pt;'><pclass=”MsoNormal”><spanlang=”EN-US”></span></p><pclass=”MsoNormal”><spanstyle=”Z-INDEX:251657216;POSITION:relative;WIDTH:645px;HEIGHT:157px;TOP:10px;LEFT:-5px”><imgalt=”文本框:\uE2D2\uE291\uE327\uE27E\uE31F\uE26A&#13;&#10;\uE2FD\uE27E\uE2ED\uE2AC\uE2ED\uE2A7&#13;&#10;\uE289\uE2B5&#13;&#10;\uE266\uE326\uE26C\uE311&#13;&#10;\uE285&#13;&#10;\uE2D2\uE291\uE301\uE27E\uE2EC\uE291\uE2B5&#13;&#10;\uE309\uE291\uE2F5\uE313\uE26C\uE313\uE28D&#13;&#10;\uE2E1\uE26C\uE328\uE291\uE2B5&#13;&#10;\uE2C1\uE26D\uE326\uE26C\uE2EC\uE291\uE2B5&#13;&#10;\uE2D2\uE291\uE328\uE317\uE27E\uE2B5&#13;&#10;\uE289\uE2B5&#13;&#10;\uE2F2\uE291\uE2BC\uE2EC\uE291\uE2F9&#13;&#10;\uE2A2\uE2C6\uE2AC\uE325&#13;&#10;”src=”9A97E5BD514EBAB4A14E19FAE9F147D3.files/image001.png”width=”645”height=”147”/></span><spanlang=”EN-US”></span></p><pclass=”MsoNormal”><spanlang=”EN-US”></span></p><pclass=”MsoNormal”><spanlang=”EN-US”></span></p><pclass=”MsoNormal”><spanlang=”EN-US”></span></p><pclass=”MsoNormal”><spanlang=”EN-US”></span></p><pclass=”MsoNormal”><spanlang=”EN-US”></span></p><pstyle=”LINE-HEIGHT:34pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:华文新魏;FONT-SIZE:36pt”lang=”EN-US”></span></strong></p><brclear=”all”/><pstyle=”TEXT-INDENT:144.15pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:华文新魏;FONT-SIZE:36pt”>民事判决书<spanlang=”EN-US”></span></span></strong></p><pstyle=”LINE-HEIGHT:27pt;TEXT-INDENT:124pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”TEXT-ALIGN:right;LINE-HEIGHT:30pt”class=”MsoNormal”align=”right”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>（<spanlang=”EN-US”>2015</span>）右民初字第<spanlang=”EN-US”>817</span>号</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>原告张葵花，</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>委托代理人丁文才，内蒙古卫益律师事务所律师。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>被告孙世国，</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>原告张葵花诉被告孙世国民间借贷纠纷一案，本院于<spanlang=”EN-US”>2015</span>年<spanlang=”EN-US”>4</span>月<spanlang=”EN-US”>23</span>日立案受理后</span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>，依法由审判员周丽华适用简易程序公开开庭进行审理，</span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>原告张葵花及其委托代理人丁文才到庭参加诉讼，被告孙世国经本院依法传票传唤，无正当理由拒不到庭参加诉讼，本案现已审理终结。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>原告张葵花诉称，被告于<spanlang=”EN-US”>2013</span>年<spanlang=”EN-US”>2</span>月<spanlang=”EN-US”>19</span>日向原告借款七万元<spanlang=”EN-US”>,</span>并约定月利率<spanlang=”EN-US”>2%</span>，一个月内还清，当时是口头约定月利率<spanlang=”EN-US”>2</span>分，没有书面约定，但被告不守信用，至今未还。无奈诉至法院，要求被告尽快偿还借款本金<spanlang=”EN-US”>700000.00</span>元及利息，利息从<spanlang=”EN-US”>2013</span>年<spanlang=”EN-US”>3</span>月<spanlang=”EN-US”>19</span>日开始按照月利率<spanlang=”EN-US”>2</span>分计算至欠款给付完毕止。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:#0f0020;FONT-SIZE:16pt”lang=”EN-US”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:#0f0020;FONT-SIZE:16pt”>被告孙世国经依法传票传唤未到庭参加诉讼。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:39.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:#0f0020;FONT-SIZE:16pt”>经审理查明</span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>，被告孙世国于<spanlang=”EN-US”>2013</span>年<spanlang=”EN-US”>2</span>月<spanlang=”EN-US”>19</span>日向原告张葵花借款人民币<spanlang=”EN-US”>70000.00</span>元，当时被告为原告出具一枚借据，内容为“借据<spanlang=”EN-US”></span>人民币柒万元正￥<spanlang=”EN-US”>70000.00</span>元<spanlang=”EN-US”></span>借用定<spanlang=”EN-US”>2013</span>年<spanlang=”EN-US”>3</span>月<spanlang=”EN-US”>19</span>日还清（期限一个月）借款人：信用社孙世国<spanlang=”EN-US”>2013</span>年<spanlang=”EN-US”>2</span>月<spanlang=”EN-US”>19</span>日”，双方当时口头约定月利率<spanlang=”EN-US”>2</span>分，被告当即给付原告当月利息<spanlang=”EN-US”>1400.00</span>元，即实际借得现金<spanlang=”EN-US”>68600.00</span>元。到期后，被告未能及时偿还欠款，原告诉至法院，要求被告偿还欠款<spanlang=”EN-US”>70000.00</span>元并按<spanlang=”EN-US”>2%</span>支付利息。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:39.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>另，原告在庭审中递交一份光盘，以此证明双方在借款时曾口头约定月利率<spanlang=”EN-US”>2</span>分。庭后经本院向被告询问，被告对双方口头约定月利率<spanlang=”EN-US”>2</span>分计算利息的事实予以承认，并自愿按本金<spanlang=”EN-US”>700000.00</span>元从<spanlang=”EN-US”>2013</span>年<spanlang=”EN-US”>3</span>月<spanlang=”EN-US”>19</span>日起支付利息。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:39.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>上述事实，有原告陈述、庭审笔录、原告递交的一枚借据原件、一份光盘，被告的询问笔录在卷予以证实，事实清楚，足以认定。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:black;FONT-SIZE:16pt”>本院认为，合法的借贷关系应受法律保护。原告张葵花递交的被告孙世国出具的一枚借据原件，可以证明被告向原告借款的事实成立，原、被告之间债权债务关系明确，本院对原告递交的借据认定有效。因在借款同时被告支付当月利息<spanlang=”EN-US”>1400.00</span>元，被告实际借到本金金额为<spanlang=”EN-US”>68600.00</span>元，对原告要求被告偿还欠款的本金数额应以实际出借的金额为准。被告孙世国虽然未能到庭参加诉讼，但庭后经本院询问，承认双方当时口头约定月利率<spanlang=”EN-US”>2</span>分计算利息的事实存在，且因未及时还款，自愿按本金<spanlang=”EN-US”>70000.00</span>元计算并支付约定利息，属当事人对自己民事权利的自行处分，不违反相关法律规定，亦不侵犯他人的权益，本院予以支持。故依照《中华人民共和国民法通则》第八十四条第二款、第九十条、第一百零八条、</span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>《中华人民共和国合同法》第二百零七条、<spanstyle=”COLOR:black”>《中华人民共和国民事诉讼法》第一百四十四条之规定，</span></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:black;FONT-SIZE:16pt”>判决如下：</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:black;FONT-SIZE:16pt”>限被告孙世国在本判决书生效之日起三十日内偿还原告张葵花欠款本金<spanlang=”EN-US”>70000.00</span>元及利息（按月利率<spanlang=”EN-US”>2</span>分计算自<spanlang=”EN-US”>2013</span>年<spanlang=”EN-US”>3</span>月<spanlang=”EN-US”>19</span>日起至欠款还清之日止）。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:15.75pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:black;FONT-SIZE:16pt”lang=”EN-US”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>如果未按本判决指定的期间履行给付金钱义务，应当依照《中华人民共和国民事诉讼法》第二百五十三条的规定，加倍支付延迟履行期间的债务利息。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>案件受理费<spanlang=”EN-US”>1550.00</span>元由被告陈金泉负担。</span></strong></p><pstyle=”LINE-HEIGHT:27pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>如不服本判决，可在判决书送达之日起十五日内向本院递交上诉状，并按对方当事人的人数提出副本，上诉于兴安盟中级人民法院。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:216.2pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>审判员<spanlang=”EN-US”></span>周丽华</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:216.2pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>二〇一五年六月十六日</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>书<spanlang=”EN-US”></span>记员<spanlang=”EN-US”></span>米法民</span></strong></p><pstyle=”LINE-HEIGHT:30pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>附：本判决书所引用法律条款内容：</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:15.75pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>《中华人民共和国民法通则》</span></strong></p><pstyle=”LINE-HEIGHT:28pt;TEXT-INDENT:32.15pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>第八十四条第二款<spanlang=”EN-US”></span>债权人有权要求债务人按照合同的约定或者依照法律的规定履行义务。</span></strong></p><pstyle=”LINE-HEIGHT:28pt;TEXT-INDENT:32.15pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>第九十条<spanlang=”EN-US”></span>合法的借贷关系受法律保护。</span></strong></p><pstyle=”LINE-HEIGHT:28pt;TEXT-INDENT:32.15pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>第一百零八条债务应当清偿。暂时无力偿还的，经债权人同意或者人民法院裁决，可以由债务人分期偿还。有能力偿还拒不偿还的，由人民法院判决强制偿还。</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:23.6pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>《中华人民共和国合同法》</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”>第二百零七条<spanlang=”EN-US”></span>借款人未按照约定的期限返还借款的，应当按照约定或者国家有关规定支付逾期利息。</span></strong></p><pstyle=”LINE-HEIGHT:30pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:black;FONT-SIZE:16pt”>《中华人民共和国民事诉讼法》</span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;COLOR:black;FONT-SIZE:16pt”>第一百四十四条<spanlang=”EN-US”></span>被告经传票传唤，无正当理由拒不到庭的，或者为经法庭许可中途退庭的，可以缺席判决。</span></strong></p><pstyle=”LINE-HEIGHT:30pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p><pstyle=”LINE-HEIGHT:30pt;TEXT-INDENT:31.5pt”class=”MsoNormal”><strong><spanstyle=”FONT-FAMILY:仿宋_GB2312;FONT-SIZE:16pt”lang=”EN-US”></span></strong></p></body></html></div>",
                segment);
//        String s = "原公诉机关山阴县人民检察院。上诉人（原审附带民事诉讼被告人）中煤财产保险股份有限公司应县支公司，营业场所：应县应山路保险公司临街东六号。负责人王居众，经理。诉讼代理人孙瑾，中煤财产保险股份有限公司朔州中心支公司职工。原审附带民事诉讼原告人山阴县流浪乞讨人员救助站。法定代表人王俊杰，站长。诉讼代理人丰改琳，山阴县法律援助中心律师。诉讼代理人李爱平，山阴县法律援助中心法律工作者。原审被告人乔某某，又名乔某，男，1966年2月3日出生于怀仁县X乡X村，身份证号为1402031966********，汉族，初中文化，司机，住X村（户籍住址：大同市X区*栋*单元*号）。";
        String[] split = s.split("。");
        for (String temp :split) {
            List<Term> seg = segment.seg(temp);
            List<String> natureList = seg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
            List<String> wordList = seg.stream().map(t -> t.word).collect(Collectors.toList());
            List<String> nameList = new LinkedList<>();
            String identity = WenshuHtmlSaveUntil.segProcessWithoutRestMap(natureList, wordList, nameList, segment);
            System.out.printf("identity: %s \n", identity);
            System.out.printf("nameList: %s \n", nameList.toString());
        }
    }

    @Test
    public void notAllowTest() {
        Segment segment = HanLPFactory.builder().segment(true);
        WenshuHtmlSaveUntil.notAllow("（一审被告）", segment);
    }
}
