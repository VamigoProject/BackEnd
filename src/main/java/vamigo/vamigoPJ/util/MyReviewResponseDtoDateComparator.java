package vamigo.vamigoPJ.util;

import vamigo.vamigoPJ.DTO.Response.MyReviewResponseDto;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class MyReviewResponseDtoDateComparator implements Comparator<MyReviewResponseDto> {
    @Override
    public int compare(MyReviewResponseDto o1,MyReviewResponseDto o2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


        int result = o1.getTime().compareTo(o2.getTime());

        return result;
    }
}
