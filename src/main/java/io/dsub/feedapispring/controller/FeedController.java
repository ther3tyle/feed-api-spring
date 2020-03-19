package io.dsub.feedapispring.controller;

import io.dsub.feedapispring.api.v1.model.FeedDetailsDto;
import io.dsub.feedapispring.api.v1.model.FeedLikeDto;
import io.dsub.feedapispring.api.v1.model.SimpleFeedDto;
import io.dsub.feedapispring.api.v1.response.FeedDetailsResponse;
import io.dsub.feedapispring.api.v1.response.FeedLikeResponse;
import io.dsub.feedapispring.api.v1.service.FeedDetailsDtoService;
import io.dsub.feedapispring.api.v1.service.FeedLikeResultDtoService;
import io.dsub.feedapispring.api.v1.service.SimpleFeedDtoService;
import io.dsub.feedapispring.exceptions.FeedNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/feeds")
public class FeedController {

    private FeedDetailsDtoService feedDetailsDtoService;
    private SimpleFeedDtoService simpleFeedDtoService;

    public FeedController(FeedDetailsDtoService feedDetailsDtoService,
                          SimpleFeedDtoService simpleFeedDtoService) {
        this.feedDetailsDtoService = feedDetailsDtoService;
        this.simpleFeedDtoService = simpleFeedDtoService;
    }

    // Pageable feed list
    @ResponseBody
    @GetMapping
    public Page<SimpleFeedDto> getFeedList(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") Integer pageSize) {

        pageSize = pageSize > 50 ? 50 : pageSize;

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "createdDate");
        return simpleFeedDtoService.getPagedList(pageRequest);
    }

    @ResponseBody
    @GetMapping(value = "/{feedId}")
    public ResponseEntity<FeedDetailsResponse> feedDetailsResponse(
            @PathVariable("feedId") Long feedId) {

        FeedDetailsDto dto = null;
        String err = null;
        HttpStatus httpStatus;
        try {
            dto = feedDetailsDtoService.getFeedDetailsDto(feedId);
            httpStatus = HttpStatus.OK;
        } catch (FeedNotFoundException e) {
            err = e.getMessage();
            httpStatus = HttpStatus.NOT_FOUND;
        }
        FeedDetailsResponse response = new FeedDetailsResponse(dto, err);
        return new ResponseEntity<>(response, httpStatus);
    }
}
