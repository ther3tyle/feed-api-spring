package io.dsub.feedapispring.controller;

import io.dsub.feedapispring.api.v1.response.FeedCommentDtoPagedResponse;
import io.dsub.feedapispring.api.v1.service.FeedCommentDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<FeedCommentDtoPagedResponse> getFeedList(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            @RequestParam(name = "flat", required = false, defaultValue = "false") String flat,
            @PathVariable(name = "feedId") Long feedId) {
        pageSize = pageSize > 50 ? 50 : pageSize;
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "createdDate");
        boolean isFlat = flat.equals("true");
        if (isFlat) {
            System.out.println("FLAT ENABLED");
        }

        FeedCommentDtoPagedResponse response =
                feedCommentDtoService.getFeedComments(feedId, pageRequest, isFlat);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
