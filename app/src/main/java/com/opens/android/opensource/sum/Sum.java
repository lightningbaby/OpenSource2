package com.opens.android.opensource.sum;

/**
 * Created by ttc on 2017/3/13.
 */

public class Sum {//综合---开源资讯和推荐博客

    private String mSumAuthorName;
    private String mSumId;
    private String mSumTitle;
    private String mSumAuthorId;
    private String mSumType;
    private String mSumPubDate;
    private String mSumCommentCount;
    private String mSumBody;

    public String getSumBody() {
        return mSumBody;
    }

    public void setSumBody(String sumBody) {
        mSumBody = sumBody;
    }

    public Sum() {
        mSumBody="default";
    }

    public String getSumAuthorName() {
        return mSumAuthorName;
    }

    public void setSumAuthorName(String sumAuthorName) {
        mSumAuthorName = sumAuthorName;
    }

    public String getSumId() {
        return mSumId;
    }

    public void setSumId(String sumId) {
        mSumId = sumId;
    }

    public String getSumTitle() {
        return mSumTitle;
    }

    public void setSumTitle(String sumTitle) {
        mSumTitle = sumTitle;
    }

    public String getSumAuthorId() {
        return mSumAuthorId;
    }

    public void setSumAuthorId(String sumAuthorId) {
        mSumAuthorId = sumAuthorId;
    }

    public String getSumType() {
        return mSumType;
    }

    public void setSumType(String sumType) {
        mSumType = sumType;
    }

    public String getSumPubDate() {
        return mSumPubDate;
    }

    public void setSumPubDate(String sumPubDate) {
        mSumPubDate = sumPubDate;
    }

    public String getSumCommentCount() {
        return mSumCommentCount;
    }

    public void setSumCommentCount(String sumCommentCount) {
        mSumCommentCount = sumCommentCount;
    }
}
