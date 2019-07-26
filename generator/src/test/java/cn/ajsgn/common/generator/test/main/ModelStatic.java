package cn.ajsgn.common.generator.test.main;

import cn.ajsgn.common.generator.db.TableConfig;
import cn.ajsgn.common.generator.db.config.TempleteVariable;

import java.util.ArrayList;
import java.util.List;

public class ModelStatic {

    //账户模块
    public static List<TableConfig> account = new ArrayList<>();

    public static String ACCOUNT = ".account";
    public static String ASSET = ".asset";

    static String ACTIVITY = ".activity";
    static String AGREEMENT = ".agreement";
    static String ASSISTANT = ".assistant";
    static String PAYMENT = ".payment";
    static String ENROLLING = ".message";

    static TempleteVariable getTempleteVariable(String bao) {
        String mokuai = "core.";
        TempleteVariable tv = new TempleteVariable()
                .setAbsPackage("arch.core.abs")
                .setConditionPackage(mokuai + "condition" + bao)
                .setConditionSuffix("Condition")
                .setDaoPackage(mokuai + "dao" + bao + ".mapper")
                .setDaoSuffix("Mapper")
                .setEntityPackage(mokuai + "model" + bao)
                .setImplPackage("impl")
                .setImplSuffix("Impl")
                .setMapperPackage("mapper"+bao)
                .setServicePackage(mokuai + "dao" + bao)
                .setServiceSuffix("Dao");
        return tv;
    }

    static {
        account.add(new TableConfig("monet", "account", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "account_company_map", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "agency", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "agency_application", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "agency_channel_agent", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "agency_portal", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "agency_portal_registered_account", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "bank_account", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "company", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "company_verify_application", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "party", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "party_apply", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "party_black_list_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "party_channel_agent", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "user", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        account.add(new TableConfig("monet", "user_verify_application", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACCOUNT)));
        
    }

    //债券模块
    public static List<TableConfig> asset = new ArrayList<>();

    static {
        asset.add(new TableConfig("monet", "asset", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_category", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_category_group", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_category_group_self_mapping", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_data", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_field", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_field_group", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_field_item", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_property", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_reject_record", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_template_field", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_template_field_mapping", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_template_field_option", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
        asset.add(new TableConfig("monet", "asset_template_options_result", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSET)));
    }


    //活动模块
    public static List<TableConfig> activity = new ArrayList<>();

    static {
        activity.add(new TableConfig("monet", "activity_black_list_city", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "activity_reject_record", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "activity_white_list_city", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "agency_portal_activity", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "auction_activity", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "auction_activity_album", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "auction_activity_album_map", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "auction_activity_category", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "auction_activity_share_stats", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "auction_activity_sub_category", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "auction_activity_view_count", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "bid", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "deal_announcement", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "platform_broadcast", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
        activity.add(new TableConfig("monet", "view_enrollments_order", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ACTIVITY)));
    }

    //支付模块
    public static List<TableConfig> payment = new ArrayList<>();

    static {
        payment.add(new TableConfig("monet", "auction_order", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "channel_pay_order_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "deposit_channel_pay_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "lock_deposit_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "lock_deposit_for_send_back_order_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "lock_enrolling_deposit_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "lock_pay_commission_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "lock_pay_order_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "lock_share_commission_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "pay_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "pay_deposit_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "pay_enrolling_commission_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "pay_view_enrollments_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "platform_channel_pay_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "release_deposit_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "release_pay_commission_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "release_pay_order_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "release_share_commission_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "release_transferred_deposit_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "transfer_deposit_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));
        payment.add(new TableConfig("monet", "transfer_deposit_for_send_back_order_action", "", "d:/JavaBean", "com._360pai", getTempleteVariable(PAYMENT)));

    }


    //辅助模块
    public static List<TableConfig> assistant = new ArrayList<>();

    static {
        assistant.add(new TableConfig("monet", "article", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "article_category", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "article_scenario", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "bank", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "banner", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "city", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "deposit", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "favorite_activity", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "instructions_content", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "notify_party_activity", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "partner_agency", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "platform_announcement", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "promotion_category_card", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "province", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "staff", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "sticky_auction_activity_album", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
        assistant.add(new TableConfig("monet", "working_day", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ASSISTANT)));
    }

    public static List<TableConfig> agreement = new ArrayList<>();

    static {

        agreement.add(new TableConfig("monet", "additional_agreement", "", "d:/JavaBean", "com._360pai", getTempleteVariable(AGREEMENT)));
        agreement.add(new TableConfig("monet", "contract_template", "", "d:/JavaBean", "com._360pai", getTempleteVariable(AGREEMENT)));
        agreement.add(new TableConfig("monet", "deal_agreement", "", "d:/JavaBean", "com._360pai", getTempleteVariable(AGREEMENT)));
        agreement.add(new TableConfig("monet", "delegation_agreement", "", "d:/JavaBean", "com._360pai", getTempleteVariable(AGREEMENT)));

    }

    //enrolling 预招商
    public static List<TableConfig> enrolling = new ArrayList<>();
   
    static {
 
//        enrolling.add(new TableConfig("monet", "asset_property", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
        enrolling.add(new TableConfig("crawler", "t_message_template", "", "d:/JavaBean", "com.tzCloud", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("crawler_v2", "t_map_house_transaction_data", "", "d:/JavaBean", "com.valuation360", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_activity_contract", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_activity_data", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_activity_favor_map", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_activity_progress", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_activity_result", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_activity_share_stats", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_activity_view_count", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "enrolling_deposit", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "favorite_enrolling_activity", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
//        enrolling.add(new TableConfig("monet", "notify_party_enrolling_activity", "", "d:/JavaBean", "com._360pai", getTempleteVariable(ENROLLING)));
    }
}
