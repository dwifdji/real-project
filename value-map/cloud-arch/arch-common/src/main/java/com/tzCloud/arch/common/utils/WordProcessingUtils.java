package com.tzCloud.arch.common.utils;

import com.aspose.words.*;
import com.aspose.words.Shape;

import java.awt.*;
import java.io.File;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/15 10:08
 */
public class WordProcessingUtils {

    static {
        AsposeWorldLisence.loadLicense();
    }

    public static void main(String[] args) throws Exception {
        ////String filePath = "D:/混合担保合同范本.doc";
        //long startTime = System.currentTimeMillis();
        //String filePath = "/Users/xdrodger/tmp/2019城市房屋拆迁补偿安置协议.doc";
        //
        //File     file     = new File(filePath);
        //String   fileName = file.getName();
        //Document doc      = new Document(filePath);
        //System.out.println(getPageCount(doc));
        //removeWatermark(doc);
        ////removeFistParagraph(doc);
        //String dir     = filePath.split("\\.")[0];
        //File   fileDir = new File(dir);
        //if (!fileDir.exists()) {
        //    fileDir.mkdirs();
        //}
        //doc.save(fileDir + File.separator + fileName);
        //
        //Document doc1 = new Document(fileDir + File.separator + fileName);
        //splitWithJPEG(doc1, dir, 2);
        //long endTime = System.currentTimeMillis();
        //System.out.println("耗时：" + (endTime-startTime)/1000);

        html2word("/data/file/output/b.doc", body);

    }

    public static Integer getPageCount(Document doc) throws Exception {
        return doc.getPageCount();
    }

    /**
     * 移除全部水印
     *
     * @param doc
     * @throws Exception
     */
    public static void removeWatermark(Document doc) throws Exception {
        for (Section sect : doc.getSections()) {
            sect.clearHeadersFooters();
            // There could be up to three different headers in each section, since we want
            // the watermark to appear on all pages, insert into all headers.
            removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_PRIMARY);
            removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_FIRST);
            removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_EVEN);
        }
        NodeCollection<Shape> shapes = (NodeCollection<Shape>) doc.getChildNodes(NodeType.SHAPE, true);
        for (Shape shape : shapes) {
            if (shape.hasImage()) {
                shape.remove();
            }
        }
    }

    /**
     * 移除指定Section的水印
     *
     * @param sect
     * @param headerType
     * @throws Exception
     */
    private static void removeWatermarkFromHeader(Section sect, int headerType) throws Exception {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header != null) {
            header.remove();
            header.removeAllChildren();
        }
    }

    public static void removeFistParagraph(Document doc) throws Exception {
        if (doc.getFirstSection().getBody().getFirstParagraph().getCount() > 0) {
            //word中的所有段落
            doc.getFirstSection().getBody().getParagraphs().get(0).remove();
        }
    }

    public static void splitWithJPEG(Document doc, String fileDir, int num) throws Exception {
        doc.save(fileDir + File.separator + "1.jpeg");
        if (doc.getPageCount() < num) {
            return;
        }
        ImageSaveOptions options = new ImageSaveOptions(SaveFormat.JPEG);
        for (int i = 1; i <= num - 1; i++) {
            options.setPageIndex(i);
            options.setPageCount(i + 1);
//            options.setJpegQuality(5);
//            options.setResolution(160);
            doc.save(fileDir + File.separator + (i + 1) + ".jpeg", options);
        }
    }

    private static void removeImage(Document doc)throws Exception{
        NodeCollection<Shape> shapes = (NodeCollection<Shape>) doc.getChildNodes(NodeType.SHAPE, true);
        for (Shape shape : shapes) {
            if (shape.hasImage()) {
                shape.remove();
            }
        }
        doc.save(doc.getOriginalFileName());
    }

    public static void removeComments(Document doc)throws Exception{
        NodeCollection comments = doc.getChildNodes(NodeType.COMMENT, true);
        comments.clear();
        doc.save(doc.getOriginalFileName());
    }

    public static void html2word(String filePath, String content) {
        try {
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            builder.insertHtml(content);
            insertHeaderFooter(builder);
            doc.save(filePath, SaveOptions.createSaveOptions(SaveFormat.DOC));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertHeaderFooter(DocumentBuilder builder) {
        try {
            Section currentSection = builder.getCurrentSection();
            PageSetup pageSetup = currentSection.getPageSetup();

            // Specify if we want headers/footers of the first page to be different from other pages.
            // You can also use PageSetup.OddAndEvenPagesHeaderFooter property to specify
            // different headers/footers for odd and even pages.
            pageSetup.setDifferentFirstPageHeaderFooter(true);
            // --- Create header for the first page. ---
            pageSetup.setHeaderDistance(20);
            builder.moveToHeaderFooter(HeaderFooterType.HEADER_FIRST);
            builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
            builder.getFont().setBold(true);
            builder.getFont().setColor(Color.BLUE);
            builder.write("特资云");

            builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
            builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
            builder.getFont().setBold(true);
            builder.getFont().setColor(Color.BLUE);
            builder.write("特资云");

            builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
            builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
            builder.getFont().setBold(true);
            builder.getFont().setColor(Color.BLUE);
            builder.write("法制引擎");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String body = "<article id=\"contract_content\" style=\"-moz-user-select:none;-webkit-user-select:none;-khtml-user-select:none;padding-top: 10px;position:relative;\">\n" +
            "\t\t\t\t\t<p style=\"text-align: center;\"><span>（文中</span><span style=\"color: #3366ff;\">蓝色字体</span><span>下载后有风险提示）</span></p>\n" +
            "<p><span style=\"font-family: 宋体;\">甲方</span>(用人单位)：</p>\n" +
            "<p>统一社会信用代码：</p>\n" +
            "<p>地址：</p>\n" +
            "<p>联系方式：</p>\n" +
            "<p>&nbsp;</p>\n" +
            "<p><span style=\"font-family: 宋体;\">乙方</span>(劳动者)：</p>\n" +
            "<p>身份证号码：</p>\n" +
            "<p>住址：</p>\n" +
            "<p>联系方式：</p>\n" +
            "<p>&nbsp;</p>\n" +
            "<p><span style=\"font-family: 宋体;\">甲乙双方就劳动关系的建立及其权利义务等事宜，根据《中华人民共和国劳动合同法》及有关的劳动法律、法规、行政规章和甲方依法制定的规章制度，遵循自愿、平等、协商一致的原则，一致同意订立本劳动合同</span>(以下简称合同)，双方共同信守合同所列各条款，并确认合同为解决争议时的依据。</p>\n" +
            "<p><strong>一、合同的类型与期限</strong></p>\n" +
            "<p>（一）经甲、乙双方协商，确定本合同为固定期限<span style=\"font-family: 宋体;\">劳动合同，自</span>_____年_____月_____日起至_____年_____月_____日止。</p>\n" +
            "<h3 class=\"box-nm\" style=\"border: 1px dashed #ed1c24; border-radius: 5px; padding: 20px 20px;\"><span class=\"lh2 w5-5\" style=\"margin-bottom: 2px; padding-left: 0; padding-right: 0;\">风险提示：</span> <br> <span class=\"lh2 w5-5\" style=\"margin-bottom: 2px; padding-left: 28px; padding-right: 0; text-indent: 2em; padding-bottom: 5px;\">根据我国法律规定，以下事项请注意： 1、劳动合同期限三个月以上不满一年的，试用期不得超过一个月；劳动合同期限一年以上不满三年的，试用期不得超过二个月；三年以上固定期限和无固定期限的劳动合同，试用期不得超过六个月。 2、同一用人单位与同一劳动者只能约定一次试用期。（也就是说不能随意延长）。 3、用人单位不得单独与员工只约定试用期并仅签订试用期协议。 4、劳动合同期限不满三个月的，不得约定试用期。 </span></h3>\n" +
            "<p>（二）试用期</p>\n" +
            "<p><span style=\"font-family: 宋体;\">甲乙双方约定试用期自</span>_____年_____月_____日起至_____年_____月_____日止。若乙方实际到岗之日与试用期约定上岗之日不符的，试用期同时提前或顺延。</p>\n" +
            "<p><strong>二、工作内容与工作地点</strong></p>\n" +
            "<p><span style=\"font-family: 宋体;\">（一）甲方聘用乙方从事</span>__________________工作。</p>\n" +
            "<p><span style=\"font-family: 宋体;\">（二）乙方的工作地点为</span>__________________，若甲方搬迁的，乙方同意变更工作地点为搬迁后的办公场所。</p>\n" +
            "<p><strong>三、工作时间与休息时间</strong></p>\n" +
            "<p>（一）乙方所在岗位执行标准工时制。</p>\n" +
            "<h3 class=\"box-nm\" style=\"border: 1px dashed #ed1c24; border-radius: 5px; padding: 20px 20px;\"><span class=\"lh2 w5-5\" style=\"margin-bottom: 2px; padding-left: 0; padding-right: 0;\">风险提示：</span> <br> <span class=\"lh2 w5-5\" style=\"margin-bottom: 2px; padding-left: 28px; padding-right: 0; text-indent: 2em; padding-bottom: 5px;\">标准工时是指平均每天工作8小时，每周不超过40小时，每周至少休息1天的工作制度，超过的时间属于加班，须支付加班费。 </span></h3>\n" +
            "<p>（二）甲方根据国家法律法规的规定给予乙方节假日休息及婚假、年休假、产假等。</p>\n" +
            "<p>（三）甲方因工作需要安排乙方延长工作时间或节假日加班加点的，乙方应服从甲方统一安排；甲方按规定支付加班加点的报酬，以保证乙方合法权益。</p>\n" +
            "<p>（四）乙方为了完成本职工作自行延长工作时间不视为加班。</p>\n" +
            "<p><strong>四、劳动报酬</strong></p>\n" +
            "<p>（一）乙方工资包括基本工资、岗位工资、绩效工资<span style=\"font-family: 宋体;\">，乙方在试用期间的基本工资为</span>_____________元/月。试用期满后，甲方以下列第_______种计算方式支付乙方工资：</p>\n" +
            "<p>1、计时工资。基本工资为_____________元/月，岗位工资和绩效工资按照公司薪酬制度确定。</p>\n" +
            "<p>2、计件工资。甲方应制定科学合理的劳动定额标准，双方及时协商约定计件单价。</p>\n" +
            "<p>3、其他形式。甲方应以法定货币形式按月支付乙方工资，支付日期为每月的_______日。乙方月工资不得低于省政府公布的最低工资标准。加班加点工资按法律法规执行。</p>\n" +
            "<p>（二）甲方可根据其实际经营情况、规章制度、对乙方考核情况，以及乙方工作年限、奖罚记录、岗位变化等，调整乙方的工资水平。乙方工作岗位调整后，其工资参照同岗位、同工种、同职务的标准执行。但不低于当地最低工资标准。</p>\n" +
            "<p><strong>五、社会保险和福利待遇</strong></p>\n" +
            "<p>（一）甲方按照国家的规定为乙方办理各项社会保险，缴纳社会保险费；</p>\n" +
            "<h3 class=\"box-nm\" style=\"border: 1px dashed #ed1c24; border-radius: 5px; padding: 20px 20px;\"><span class=\"lh2 w5-5\" style=\"margin-bottom: 2px; padding-left: 0; padding-right: 0;\">风险提示：</span> <br> <span class=\"lh2 w5-5\" style=\"margin-bottom: 2px; padding-left: 28px; padding-right: 0; text-indent: 2em; padding-bottom: 5px;\">社保条款是劳动合同的必备条款，缴纳社保是强制性义务，建议及时为员工缴纳社保。 否则可能存在以下风险： 1、员工以此为由要求解除劳动合同，并要求支付经济补偿金。 2、员工可向劳动监察部门、社保部门举报，需限期补缴并承担滞纳金，限期未补缴的，可能面临行政罚款。 3、员工发生工伤、生育、医疗保险等范围内的费用的，将有权要求公司承担全部费用。 若实在因为员工不愿意缴纳、员工流动性太大等原因不缴纳的，也建议购买雇主责任险，降低贵司的风险，减轻经济负担，但无法规避未缴纳社保的风险。</span></h3>\n" +
            "<p>（二）依法应由乙方个人负担的社会保险费，甲方从乙方应得工资中扣缴。</p>\n" +
            "<p><strong>六、劳动保护、劳动条件和职业危害防护</strong></p>\n" +
            "<p>（一）甲方建立健全业务操作规程、工作规范和劳动安全卫生制度及其标准。甲方对可能产生职业病危害的岗位，对乙方履行告知义务，并做好劳动过程中职业危害的预防工作，乙方应严格遵守相关操作流程与安全制度。</p>\n" +
            "<p>（二）甲方为乙方提供符合国家规定的劳动条件及安全卫生的工作环境，并依照企业生产经营特点及有关规定为乙方提供劳动防护用品，乙方应严格按要求穿戴劳防用品。</p>\n" +
            "<p>（三）甲方对乙方进行职业技术、安全卫生、规章制度等必要的教育与培训，乙方应认真参加甲方组织的各项必要的教育培训。</p>\n" +
            "<p><strong>七、劳动合同的变更、解除</strong><strong>和终止</strong></p>\n" +
            "<p>（一）订立合同所依据的法律、行政法规、规章发生变化，合同应变更相关内容。</p>\n" +
            "<p>（二）订立合同所依据的客观情况发生重大变化，致使合同无法履行的，经协商同意，可以变更合同相关内容或解除。</p>\n" +
            "<p>（三）乙方在试用期内被证明不符合甲方用人标准或录用条件的，甲方提前三日通知乙方解除合同。</p>\n" +
            "<p>（四）乙方有下列情形之一的，甲方可立即解除合同，辞退乙方，如造成甲方经济损失的，乙方应承担赔偿责任：</p>\n" +
            "<p>1、如无特别约定的，合同签约后5天内员工未能上岗的。</p>\n" +
            "<p>2、因乙方未能在15天内提供其被录用的相关资料，至使甲方无法办理录用及社会保险缴纳手续的。</p>\n" +
            "<p>3、乙方被查实在应聘时向甲方提供的其个人资料是虚假的，包括但不限于：离职证明、身份证明、户籍证明、学历证明、体检证明等是虚假或伪造的；应聘前患有精神病、传染性疾病及其它严重影响工作的疾病而在应聘时未声明的；应聘前曾受到其它单位记过、留用察看、开除或除名等严重处分、或者有赌博、吸毒等劣迹而在应聘时未声明的；应聘前曾被劳动教养、拘留或者依法追究刑事责任而在应聘时未声明的等。</p>\n" +
            "<p>4、严重违反甲方的劳动纪律、员工手册或规章制度。</p>\n" +
            "<p>5、法律法规规定的其他情形。</p>\n" +
            "<p>（五）甲乙双方解除劳动关系的，乙方应当按照双方约定或甲方的相关规章制度，办理工作交接，包括但不限于乙方应依据甲方要求交接经办的业务工作、归还当时占有的公司财物和文件资料、结清借款以及签署相关工作交接单等其他相关手续。双方同意在签署交接单后方视为办结工作交接。乙方工资、补偿金等款项在办结工作交接时支付。</p>\n" +
            "<p><strong>八、保密义务</strong></p>\n" +
            "<p>1、乙方在工作期间所产生的工作成果（包括但不限于技术资料、开发、总结的概念、创意、文件及所出版的书籍，工作日志、培训材料、操作手册、音像资料等），无论何种形态（文字、图画、音像等），其全部知识产权均属于甲方所有。</p>\n" +
            "<p>2、乙方应当严格履行保密义务，乙方从甲方处获知的一切与甲方相关的信息均属于保密信息，包括但不限于甲方经营信息、员工信息、甲方客户信息、技术秘密及其信息载体，否则视为乙方严重违反规章制度，甲方有权解除劳动合同，并要求乙方赔偿损失。</p>\n" +
            "<p>3、鉴于乙方在甲方从事的岗位，已经（或将要）知悉甲方的商业秘密，并获得增进知识、经验、技能的机会，乙方在职期间，应当遵守以下竞业禁止义务：</p>\n" +
            "<p><span style=\"font-family: 宋体;\">（</span>1）不得自营或者为他人经营与甲方同类或类似的业务，自营或为他人经营包括以专职或兼职的方式到相关公司工作，或成为相关公司股东等；</p>\n" +
            "<p><span style=\"font-family: 宋体;\">（</span>2）不得受聘于与甲方同类或类似行业的市场经济主体（包括正式聘用或以其他方式提供劳务、技术指导、咨询等服务方式）；</p>\n" +
            "<p><span style=\"font-family: 宋体;\">（</span>3）不得直接或间接或协助他人劝诱甲方其他员工离开公司；</p>\n" +
            "<p><span style=\"font-family: 宋体;\">（</span>4）不得直接或间接影响或试图影响甲方的客户关系，将甲方客户引导与乙方或其他方私下交易或达成交易意向（包括但不限于甲方意向客户、正式客户、已合作过的客户等）；</p>\n" +
            "<p><span style=\"font-family: 宋体;\">（</span>5）其他违反竞业限制，影响甲方权益的行为。</p>\n" +
            "<p>4、若乙方违反本条，视为乙方严重违反本合同及公司规章制度，甲方有权立即解除劳动合同。若给甲方造成其他损失的，甲方有权追究其法律责任。</p>\n" +
            "<p><strong>九、劳动争议处理</strong></p>\n" +
            "<p>因履行本合同发生的劳动争议，当事人可以向本单位劳动争议调解委员会申请调解；不愿调解或调解不成，当事人一方也可以直接向劳动争议仲裁委员会申请仲裁。对裁决不服的，可以依法向人民法院提起诉讼。</p>\n" +
            "<p><strong>十、</strong><strong>声明与确认</strong></p>\n" +
            "<p>1、乙方确认，甲方已如实告知乙方工作内容、工作条件、用人要求、工作地点、职业危害、安全生产状况、劳动报酬、社会保险等情况，并已将工作过程中可能产生的职业病危害及其后果、职业病防护措施和待遇以及乙方要求了解的其它情况告知乙方。</p>\n" +
            "<p>2、乙方确认，其向甲方提供或填写的入职登记表、履历上的信息完全真实有效。乙方与其它用人单位不存在劳动关系，亦不受竞业限制义务的限制。</p>\n" +
            "<p><strong>十一、通知与送达</strong></p>\n" +
            "<p>1、甲乙双方在劳动合同履行过程中相互发出或者提供的所有通知、文件、文书、资料等，均可以当面交付或以本劳动合同所列明的通讯地址履行送达义务。一方如果迁址或变更电话，应当及时书面通知另一方，否则自行承担不利后果。</p>\n" +
            "<p>2、乙方确认已收悉公司现有的规章制度，并认真阅读、理解甲方制定的规章制度，并同意遵守执行。此处的规章制度包括《员工手册》等长期的综合的制度，也包括甲方依照法律程序订立且以书面或内部电子网络等方式向乙方公示的通知、须知、办法和细则等单项规章制度，其中包括但不限于员工手册、职位说明、安全准则等。</p>\n" +
            "<p>3、甲方规章制度将通过下述方式公示乙方：(a)书面传阅；(b)电子邮件送达；(c)甲方办公网络或公司网页；(d)其他合理可行的方式发布给全体员工或符合该规章制度适用范围的相关员工，乙方确认将随时查收。</p>\n" +
            "<p><strong>十二、其他</strong></p>\n" +
            "<p>本合同一式两份，双方各执一份。</p>\n" +
            "<p align=\"center\">（以下无正文）</p>\n" +
            "<p>甲方签字：</p>\n" +
            "<p>________年_______月_______日</p>\n" +
            "<p>乙方签字：</p>\n" +
            "<p>________年_______月_______日</p>\t\t\t\t\t<div class=\"fbxq-wufafuzhi\">\n" +
            "\t\t\t\t\t\t<h5>无法复制，请下载范本查看<span class=\"bolang\">~~</span></h5>\n" +
            "\t\t\t\t\t\t<span class=\"sanjiaoxing\"><img src=\"img/new/fbjh/sanjiao.png\" alt=\"三角\"></span>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</article>";
}
