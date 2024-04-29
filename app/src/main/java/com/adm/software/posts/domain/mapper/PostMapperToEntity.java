package com.adm.software.posts.domain.mapper;

import com.adm.software.posts.data.entities.PostEntity;
import com.adm.software.posts.data.model.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class PostMapperToEntity {
    public static PostEntity objcetToMapper(PostResponse postResponse) {

        return new PostEntity(
                postResponse.getTitle(),
                postResponse.getBody());
    }

    public static PostResponse entityToMapper(PostEntity postEntity) {

        return new PostResponse(
                postEntity.getTitle(),
                postEntity.getBody());
    }

    public static List<PostEntity> objectListToMapper(List<PostResponse> postResponseList) {

        List<PostEntity> postEntityList = new ArrayList<>();

        for (int i = 0; i < postResponseList.size(); i++) {

            postEntityList.add(new PostEntity(
                    postResponseList.get(i).getTitle(),
                    postResponseList.get(i).getBody()));
        }

        return postEntityList;
    }

    public static List<PostResponse> entityListToMapper(List<PostEntity> postEntityList) {

        List<PostResponse> postReponseList = new ArrayList<>();

        for (int i = 0; i < postEntityList.size(); i++) {

            postReponseList.add(new PostResponse(
                    postEntityList.get(i).getTitle(),
                    postEntityList.get(i).getBody()));
        }

        return postReponseList;
    }
}
