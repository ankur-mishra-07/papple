package com.ifocus.papple.model;

/**
 * Created by iFocus_2 on 19-04-2016.
 */
public class EventCreator {

    private String user_id;
    private String event_topic;
    private String event_desc;
    private String event_category;
    private String post_type;
    private String post_status;

    public EventCreator(String user_id, String event_topic, String event_desc,
                        String event_category, String post_type, String post_status) {
        this.user_id = user_id;
        this.event_topic = event_topic;
        this.event_desc = event_desc;
        this.event_category = event_category;
        this.post_type = post_type;
        this.post_status = post_status;
    }

}
