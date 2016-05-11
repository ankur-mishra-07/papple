package com.ifocus.papple.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by iFocus_2 on 22-04-2016.
 */

public class PostData {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("post_name")
    @Expose
    private String postName;
    @SerializedName("post_category")
    @Expose
    private String postCategory;
    @SerializedName("post_description")
    @Expose
    private String postDescription;
    @SerializedName("post_id")
    @Expose
    private Integer postId;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @SerializedName("user_name")
    @Expose
    private String user_name;
    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The postType
     */
    public String getPostType() {
        return postType;
    }

    /**
     *
     * @param postType
     * The post_type
     */
    public void setPostType(String postType) {
        this.postType = postType;
    }

    /**
     *
     * @return
     * The postName
     */
    public String getPostName() {
        return postName;
    }

    /**
     *
     * @param postName
     * The post_name
     */
    public void setPostName(String postName) {
        this.postName = postName;
    }

    /**
     *
     * @return
     * The postCategory
     */
    public String getPostCategory() {
        return postCategory;
    }

    /**
     *
     * @param postCategory
     * The post_category
     */
    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    /**
     *
     * @return
     * The postDescription
     */
    public String getPostDescription() {
        return postDescription;
    }

    /**
     *
     * @param postDescription
     * The post_description
     */
    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    /**
     *
     * @return
     * The postId
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     *
     * @param postId
     * The post_id
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}