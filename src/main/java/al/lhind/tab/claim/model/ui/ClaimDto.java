package al.lhind.tab.claim.model.ui;

import al.lhind.tab.claim.model.ClaimStatus;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClaimDto {
    private Long claimId;
    private ClaimStatus status;
    private String email;
    private String description;
    private LocalDateTime createdDate;
    private String ip;
}
