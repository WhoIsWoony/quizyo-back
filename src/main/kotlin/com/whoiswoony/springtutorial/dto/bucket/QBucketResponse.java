package com.whoiswoony.springtutorial.dto.bucket;

import com.querydsl.core.types.ConstructorExpression;
import com.whoiswoony.springtutorial.dto.bucket.BucketResponse;
import javax.annotation.processing.Generated;

/**
 * com.whoiswoony.springtutorial.dto.bucket.QBucketResponse is a Querydsl Projection type for BucketResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBucketResponse extends ConstructorExpression<BucketResponse> {

    private static final long serialVersionUID = -1176992426L;

    public QBucketResponse(com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<Integer> shareNum, com.querydsl.core.types.Expression<Integer> viewNum, com.querydsl.core.types.Expression<Long> id) {
        super(BucketResponse.class, new Class<?>[]{String.class, String.class, int.class, int.class, long.class}, title, description, shareNum, viewNum, id);
    }

}

