package io.dsub.feedapispring.util;

import io.dsub.feedapispring.api.v1.model.*;
import io.dsub.feedapispring.domain.Feed;
import io.dsub.feedapispring.domain.FeedComment;
import io.dsub.feedapispring.domain.TrackedDomain;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperBean {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Feed.class, FeedDetailsDto.class).addMappings(mapping -> {
            mapping.map(TrackedDomain::getFormattedCreateDate, FeedDetailsDto::setCreatedDate);
            mapping.map(TrackedDomain::getFormattedlastModified, FeedDetailsDto::setLastModified);
        });

        modelMapper.typeMap(FeedComment.class, FeedCommentDto.class).addMappings(mapping ->
                mapping.map(TrackedDomain::getFormattedCreateDate, FeedCommentDto::setCreatedDate));

        modelMapper.typeMap(FeedComment.class, FlatFeedCommentDto.class).addMappings(mapping -> {
            mapping.map(TrackedDomain::getFormattedCreateDate, FlatFeedCommentDto::setCreatedDate);
            mapping.map(FeedComment::getChildIdList, FlatFeedCommentDto::setChildIdList);
            mapping.map(FeedComment::getParentId, FlatFeedCommentDto::setParentId);
        });

        modelMapper.typeMap(FeedComment.class, NestedFeedCommentDto.class).addMappings(mapping -> {
            mapping.map(TrackedDomain::getFormattedCreateDate, NestedFeedCommentDto::setCreatedDate);
            mapping.map(FeedComment::getChildComments, NestedFeedCommentDto::setChild);
            mapping.map(FeedComment::getParentId, NestedFeedCommentDto::setParentId);
        });

        modelMapper.typeMap(FlatFeedCommentDto.class, FeedCommentDto.class).addMappings(mapping -> {
            mapping.map(FlatFeedCommentDto::getChildIdList, FeedCommentDto::setChildIdList);
            mapping.map(FlatFeedCommentDto::getParentId, FeedCommentDto::setParentId);
        });

        modelMapper.typeMap(NestedFeedCommentDto.class, FeedCommentDto.class).addMappings(mapping -> {
            mapping.map(NestedFeedCommentDto::getChild, FeedCommentDto::setChild);
            mapping.map(NestedFeedCommentDto::getParentId, FeedCommentDto::setParentId);
            mapping.skip(FeedCommentDto::setChildIdList);
        });

        modelMapper.typeMap(Feed.class, SimpleFeedDto.class).addMappings(mapping ->
                mapping.map(TrackedDomain::getFormattedCreateDate, SimpleFeedDto::setCreatedDate));

        modelMapper.validate();

        return modelMapper;
    }

    @Bean(value = "addFeedCommentDtoMapper")
    public TypeMap<AddFeedCommentDto, FeedComment> singleFeedCommentTypeMap() {
        TypeMap<AddFeedCommentDto, FeedComment> typeMap = new ModelMapper().emptyTypeMap(AddFeedCommentDto.class, FeedComment.class);
        typeMap.addMappings(mapping -> {
            mapping.map(AddFeedCommentDto::getUserId, FeedComment::setUserId);
            mapping.map(AddFeedCommentDto::getText, FeedComment::setText);
            mapping.skip(FeedComment::setParentComment);
            mapping.skip(FeedComment::setChildComments);
            mapping.skip(FeedComment::setFeed);
        });
        return typeMap;
    }
}
