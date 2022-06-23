package vamigo.vamigoPJ.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vamigo.vamigoPJ.DTO.Request.RequestReplyDto;
import vamigo.vamigoPJ.entity.Reply;
import vamigo.vamigoPJ.entity.Review;
import vamigo.vamigoPJ.entity.User;
import vamigo.vamigoPJ.repository.ReplyRepository;
import vamigo.vamigoPJ.repository.ReviewRepository;
import vamigo.vamigoPJ.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReplyService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;


    @Transactional
    public Long replyCreate(RequestReplyDto requestReplyDto){
        Optional<User> user = userRepository.findById(requestReplyDto.getUid());
        Optional<Review> review = reviewRepository.findById(requestReplyDto.getReviewId());
        Long id = replyRepository.save(Reply.builder().review(review.get()).user(user.get()).comment(requestReplyDto.getComment()).build()).getReplyId();
        return id;
    }

    @Transactional
    public String replyDelete(RequestReplyDto requestReplyDto){
        Optional<User> user = userRepository.findById(requestReplyDto.getUid());
        List<Reply> reply = replyRepository.findByUser_uid(requestReplyDto.getUid());
        Optional<Review> review = reviewRepository.findById(requestReplyDto.getReviewId());
        for(int i = 0 ; i < reply.size(); i++){
            if(reply.get(i).getReview().getId().equals(requestReplyDto.getReviewId())){

                replyRepository.deleteById(reply.get(i).getReplyId());
                return "Success";
            }
        }
        return "Fail";
    }
}
