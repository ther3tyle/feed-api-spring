package io.dsub.feedapispring.controller;

import io.dsub.feedapispring.api.v1.model.FeedLikeDto;
import io.dsub.feedapispring.api.v1.response.FeedLikeResponse;
import io.dsub.feedapispring.api.v1.service.FeedLikeDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class FeedLikeController {

    FeedLikeDtoService service;

    public FeedLikeController(FeedLikeDtoService service) {
        this.service = service;
    }

    @PutMapping("/feeds/{feedId}/likes/{userId}")
    @ResponseBody
    public ResponseEntity<FeedLikeResponse> addLike(
            @PathVariable("feedId") Long feedId,
            @PathVariable("userId") Long userId) {
        System.out.println("hello World");

        FeedLikeDto dto = new FeedLikeDto(feedId, userId);
        System.out.println(dto);
        FeedLikeResponse response = service.addFeedLike(dto);

        HttpHeaders headers = new HttpHeaders();

        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else {
            headers.add("Location",
                    "/feeds/" + response.getData().getFeedId()
                            + "/likes/" + response.getData().getUserId());
            return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/feeds/{feedId}/likes/{userId}")
    @ResponseBody
    public ResponseEntity<FeedLikeResponse> removeLike(
            @PathVariable("feedId") Long feedId,
            @PathVariable("userId") Long userId) {
        FeedLikeDto dto = new FeedLikeDto(feedId, userId);
        FeedLikeResponse response = service.removeFeedLike(dto);
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/feeds/{feedId}/likes/*", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<FeedLikeResponse> getLike () {
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }
}
