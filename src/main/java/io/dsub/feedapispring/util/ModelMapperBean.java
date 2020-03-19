package io.dsub.feedapispring.util;

import io.dsub.feedapispring.api.v1.model.FeedCommentDto;
import io.dsub.feedapispring.api.v1.model.FeedDetailsDto;
import io.dsub.feedapispring.api.v1.model.SimpleFeedDto;
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
//        modelMapper.typeMap(FeedComment.class, FeedCommentDto.class, "defaultMap").addMappings(mapping -> {
//            mapping.map(TrackedDomain::getFormattedCreateDate, FeedCommentDto::setCreatedDate);
//            mapping.map(FeedComment::getParentId, FeedCommentDto::setParentId);
//        });
//        modelMapper.typeMap(FeedComment.class, FeedCommentDto.class, "flatMap").addMappings(mapping -> {
//            mapping.skip(FeedCommentDto::setChild);
//            mapping.map(TrackedDomain::getFormattedCreateDate, FeedCommentDto::setCreatedDate);
//            mapping.map(FeedComment::getChildIdList, FeedCommentDto::setChildIdList);
//            mapping.map(FeedComment::getParentId, FeedCommentDto::setParentId);
//        });
        modelMapper.typeMap(Feed.class, SimpleFeedDto.class).addMappings(mapping ->
                mapping.map(TrackedDomain::getFormattedCreateDate, SimpleFeedDto::setCreatedDate));
        modelMapper.validate();
        return modelMapper;
    }

    @Bean(name = "flatFeedCommentConverter")
    public Converter<FeedComment, FeedCommentDto> flatFeedCommentConverter() {
        return context -> {
            FeedComment s = context.getSource();
            FeedCommentDto d = context.getDestination();

            d.setUserId(s.getUserId());
            d.setText(s.getText());
            d.setChildIdList(s.getChildIdList());
            d.setParentId(s.getParentId());
            d.setCreatedDate(s.getFormattedCreateDate());
            d.setId(s.getId());

            return d;
        };
    }

    @Bean
    public TypeMap<FeedComment, FeedCommentDto> flatTypeMap() {
        TypeMap<FeedComment, FeedCommentDto> typeMap = new ModelMapper().emptyTypeMap(FeedComment.class, FeedCommentDto.class);
        typeMap.addMappings(mapping -> {
            mapping.map(FeedComment::getId, FeedCommentDto::setId);
            mapping.map(FeedComment::getText, FeedCommentDto::setText);
            mapping.map(FeedComment::getChildIdList, FeedCommentDto::setChildIdList);
            mapping.map(FeedComment::getParentId, FeedCommentDto::setParentId);
            mapping.map(FeedComment::getUserId, FeedCommentDto::setUserId);
            mapping.map(FeedComment::getFormattedCreateDate, FeedCommentDto::setCreatedDate);
        });
        return typeMap;
    }
}
