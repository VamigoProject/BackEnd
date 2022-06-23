package vamigo.vamigoPJ.DTO.Request;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor

public class RequestReviewReportDto {
    private Long uid;
    private Long reviewId;
    private Boolean spoiler;
    private Boolean curse;
    private Boolean etc;
    private Boolean ero;

    @Builder
    public RequestReviewReportDto(Long uid, Long reviewId, Boolean spoiler, Boolean curse, Boolean etc, Boolean ero) {
        this.uid = uid;
        this.reviewId = reviewId;
        this.spoiler = spoiler;
        this.curse = curse;
        this.etc = etc;
        this.ero = ero;
    }
}
