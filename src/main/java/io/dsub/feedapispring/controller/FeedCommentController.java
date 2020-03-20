package io.dsub.feedapispring.controller;

import io.dsub.feedapispring.api.v1.model.FeedCommentDto;
import io.dsub.feedapispring.api.v1.model.SimpleFeedCommentDto;
import io.dsub.feedapispring.api.v1.response.FeedCommentDtoPagedResponse;
import io.dsub.feedapispring.api.v1.response.FeedCommentResponse;
import io.dsub.feedapispring.api.v1.service.FeedCommentDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/")
public class FeedCommentController {

    private FeedCommentDtoService feedCommentDtoService;

    public FeedCommentController(FeedCommentDtoService feedCommentDtoService) {
        this.feedCommentDtoService = feedCommentDtoService;
    }

    @ResponseBody
    @GetMapping("/feeds/{feedId}/comments")
    public ResponseEntity<FeedCommentDtoPagedResponse> getCommentList(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            @RequestParam(name = "flat", required = false, defaultValue = "30") byte[] flat,
            @PathVariable(name = "feedId") Long feedId) {


        pageSize = pageSize > 50 ? 50 : pageSize;
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "createdDate");

        boolean isFlat = false;
        try {
            isFlat = Integer.parseInt(Arrays.toString(flat)) == 1;
        } catch (NumberFormatException e) {
            if (new String(flat).equals("true")) {
                isFlat = true;
            }
        }

        FeedCommentDtoPagedResponse response =
                feedCommentDtoService.getFeedComments(feedId, pageRequest, isFlat);

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/feeds/*/comments/{commentId}")
    public ResponseEntity<FeedCommentResponse> getComment(@PathVariable Long commentId) {
        FeedCommentResponse response = feedCommentDtoService.findById(commentId);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ResponseBody
    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<FeedCommentResponse> postFeedComment(
            @RequestBody FeedCommentDto addCommentDto, @PathVariable Long feedId) {

        FeedCommentResponse response = feedCommentDtoService.addFeedComments(feedId, addCommentDto);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",
                "/feeds/" + feedId + "/comments/" + response.getData().getId());
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping("/feeds/{feedId}/comments")
    public ResponseEntity<FeedCommentResponse> updateFeedComment(
            @RequestBody SimpleFeedCommentDto requestDto, @PathVariable Long feedId) {

        FeedCommentResponse response = feedCommentDtoService.updateFeedComment(feedId, requestDto);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",
                "/feeds/" + feedId + "/comments/" + response.getData().getId());
        response.setData(null);
        return new ResponseEntity<>(response, headers, HttpStatus.ACCEPTED);
    }

    @ResponseBody
    @DeleteMapping("/feeds/{feedId}/comments/{commentId}")
    public ResponseEntity<String> deleteFeedComment(
            @PathVariable Long feedId, @PathVariable Long commentId) {

        // TODO: 2020/03/20 ADD RESPONSE TEXT

        String response = feedCommentDtoService.deleteFeedComment(feedId, commentId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}

