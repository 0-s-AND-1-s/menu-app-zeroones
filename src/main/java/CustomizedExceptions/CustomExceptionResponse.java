package CustomizedExceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CustomExceptionResponse {
    private String msg;
    private LocalDateTime dateTime;
    private String status;
}
