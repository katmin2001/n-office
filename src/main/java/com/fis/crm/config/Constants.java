package com.fis.crm.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final char DEFAULT_ESCAPE_CHAR_QUERY = '\\';

    private Constants() {
    }

    public static final String STATUS_APPROVE_0 = "0";
    public static final String STATUS_APPROVE_1 = "1";
    public static final String STATUS_APPROVE_2 = "2";
    public static final String STATUS_ACTIVE = "1";
    public static final String TIME_ZONE_DEFAULT = "GMT+7";
    public static final int WIDTH = 255;
    public static final String STATUS_STILL_VALIDATED = "1";
    public static final String STATUS_EXPIRE = "2";

    public static final short SUCCESS = 200;
    public static final char DEFAULT_CONTAINS_CHAR = '%';

    public static final Integer TIME_TYPE_DATE = 1;
    public static final Integer TIME_TYPE_MONTH = 2;
    public static final Integer TIME_TYPE_QUARTER = 3;
    public static final Integer TIME_TYPE_YEAR = 4;


    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_DDMMYYY = "dd/MM/yyyy";

    public static final String STATUS_ACTIVE_STR = "1";
    public static final String TICKET_STATUS_PROCESSING = "2";
    public static final Integer STATUS_INACTIVE = 0;
    public static final String STATUS_INACTIVE_STR = "0";
    public static final Integer STATUS_ACTIVE_INT = 1;

    public static final String DELETE = "delete";

    public static final Long TAB_PROCESSING = 1L;
    public static final Long TAB_DONE_CONFIRM = 2L;

    public static final Long NO_DUPLICATE_FILTER = 0L;
    public static final Double DOWNLOAD_DEFAULT = 0D;
    public static final Long DUPLICATE_FILTER_IN_FILE = 1L;
    public static final Long DUPLICATE_FILTER_IN_CAMPAIGN = 2L;


    public interface ACTION_LOG_TYPE {
        Long INSERT = 1L;
        Long UPDATE = 2L;
        Long DELETE = 3L;//Chuyển trạng thái
        Long EXPORT = 5L;//Xuất excel
        String STATE_CHANGE = "4";//Chuyển trạng thái (bỏ)
    }

    public interface STATUS {
        Long ACTIVE = 1L;
    }

    public static final String CBX_CODE = "22";
    public static final Map<Long, String> MAP_TITLE_SATUS = new HashMap<Long, String>() {{
        put(1L, "export.productivity.status.1");
        put(2L, "export.productivity.status.2");
        put(3L, "export.productivity.status.3");
        put(4L, "export.productivity.status.4");
        put(5L, "export.productivity.status.5");
    }};

    public static Map<Long, String> getMapTitleSatus() {
        return MAP_TITLE_SATUS;
    }

    public static final List<String> FILE_EXT = Arrays.asList("jpg", "jpeg", "png", "bmp", "tiff", "pdf", "xls", "xlsx", "doc", "docx");


    public interface CALL_STATUS {
        final String YET_CALL = "1";
        final String CALLED = "2";
        final String ERROR = "3";
    }

    public static final String RADIO_TYPE = "1";
    public static final String RANKING_TYPE = "4";

    public interface AUDIO {
        String RECORD = "/record";

        String CONTENT_TYPE = "Content-Type";
        String CONTENT_LENGTH = "Content-Length";
        String AUDIO_CONTENT = "audio/";
        String CONTENT_RANGE = "Content-Range";
        String ACCEPT_RANGES = "Accept-Ranges";
        String BYTES = "bytes";
        int BYTE_RANGE = 1024;
    }


    public interface EGP {
        String ACCEPT_LANGUAGE_VI = "vi";
        String AUTHORIZATION = "Authorization";
    }

    public interface EVALUATE_RESULT_STATUS {
        String YET_EVALUATE = "0";
        String EVALUATED = "1";
        String RE_EVALUATE = "2";
    }

    public interface IN_OUT_EVALUATE_STATUS {
        String YET_EVALUATE = "1";
        String EVALUATED = "2";
    }

    public interface CHANNEL_TYPE {
        String IN = "1";
        String OUT = "2";
    }

    public interface MENU_ID {
        Long VOC = 1L;
        Long OMS = 2L;
        Long QA = 3L;
        Long KMS = 4L;
        Long EMAIL_MARKETING = 5L;
        Long REPORT = 6L;
        Long SMS_MARKETING = 7L;
        Long SYSTEM_MANAGEMENT = 8L;
    }

    public interface MENU_ITEM_ID {
        Long configVOC = 1L;
        Long report_complaint_detail = 2L;
        Long report_process_time = 3L;
        Long report_contact_center = 4L;
        Long report_complaint = 5L;
        Long event_receive = 6L;
        Long search_ticket = 8L;
        //
        Long campain_script = 9L;
        Long callout_campaign = 11L;
        Long campaign_resource = 12L;
        Long campaign_blacklist = 14L;
        Long campaign_group_user = 25L;
        Long campaign = 10L;
        Long record_call_results = 19L;
        Long report_productivity_callout_campaign = 22L;
        Long report_render_statistics_question = 101L;
        Long report_render_call = 100L;
        Long report_render_detail_output_call = 102L;
        Long campaign_perform_information = 46L;
//        Long record_call_results = 36L;
        //
        Long criteria_group = 32L;
        Long evaluate_assignment = 34L;
        Long criteria_rating = 37L;
        Long criteria = 38L;
        Long search_work_list = 39L;
        Long evaluate_list = 40L;
        Long report_evaluate_score = 41L;
        Long report_evaluate_rating = 42L;
        Long report_evaluate_criteria = 43L;
        Long call_information_for_evaluation = 53L;
        //
        Long kms_management = 45L;
        //
        Long campaign_email_blacklist = 13L;
        Long email_config = 15L;
        Long campaign_email_marketing = 17L;
        Long config_auto_email = 18L;
        Long send_email_one = 21L;
        Long campaign_email_resource = 23L;
        Long report_campaign_email_marketing = 27L;
        Long report_sent_email_marketing = 28L;
        //
        Long campaign_sms_black_list = 16L;
        Long campaign_sms_marketing = 20L;
        Long report_campaign_sms_marketing = 24L;
        Long report_sent_sms_marketing = 26L;
        Long send_sms_one = 29L;
        Long campaign_sms_resource = 30L;
        Long config_auto_sms = 103L;
        //
        Long general_directory_management = 7L;
        Long users = 31L;
        Long roles_management = 48L;
        Long group_call_out = 49L;
        Long action_log = 52L;

        Long MANAGEMENT_ROLE = 46L;
    }

    public interface CHANNEL {
        Integer EMAIL = 1;
        Integer SMS = 2;
    }

    public interface CALL {
        Integer OUT = 1;
        Integer INT = 2;
    }

    public static final String STATUS_NOT_IN_PROCESS = "Chưa xử lý";

    public interface TYPE_3cx {
        String CREATE = "create";
        String UPDATE = "update";
        String DELETE = "delete";
    }

}
