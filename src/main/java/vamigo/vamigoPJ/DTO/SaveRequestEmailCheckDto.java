package vamigo.vamigoPJ.DTO;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vamigo.vamigoPJ.entity.EmailCheck;

@NoArgsConstructor
@Getter
@Data
public class SaveRequestEmailCheckDto {
    private String mail;
    private String code;

    @Builder
    public SaveRequestEmailCheckDto(String mail, String code){
        this.mail = mail;
        this.code = code;
    }


    public EmailCheck toEntitiy(){
        return EmailCheck.builder().mail(mail).code(code).build();

    }
}
