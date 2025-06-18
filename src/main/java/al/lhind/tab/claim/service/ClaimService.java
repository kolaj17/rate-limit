package al.lhind.tab.claim.service;

import al.lhind.tab.claim.exception.CustomException;
import al.lhind.tab.claim.exception.NotFoundException;
import al.lhind.tab.claim.model.Claim;
import al.lhind.tab.claim.model.ClaimStatus;
import al.lhind.tab.claim.model.ui.ClaimDto;
import al.lhind.tab.claim.repository.ClaimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
public class ClaimService {
    private final ClaimRepository repository;

    public ClaimService(ClaimRepository repository) {
        this.repository = repository;
    }

    public ClaimDto getClaim(Long id) throws CustomException {
        Claim claim = findClaim(id);
        return ClaimMapper.toDto(claim);
    }

    public ClaimDto createClaim(ClaimDto claimDto, String remoteAddress) {
        log.info("Inside service createClaim");
        LocalDateTime date = LocalDateTime.now();
        claimDto.setStatus(ClaimStatus.PENDING);
        claimDto.setCreatedDate(date);
        claimDto.setIp(remoteAddress);
        Claim claim = ClaimMapper.toEntity(claimDto);
        claim = repository.save(claim);
        return ClaimMapper.toDto(claim);
    }

    private Claim findClaim(Long id) throws CustomException {
        Supplier<CustomException> notFound = () -> new NotFoundException("Claim not found");
        return repository.findById(id).orElseThrow(notFound);
    }

    private static class ClaimMapper {
        private static ClaimDto toDto(Claim claim) {
            ClaimDto result = new ClaimDto();
            result.setClaimId(claim.getId());
            result.setEmail(claim.getEmail());
            result.setDescription(claim.getDescription());
            result.setStatus(claim.getStatus());
            result.setCreatedDate(claim.getCreatedDate());
            result.setIp(claim.getIp());
            return result;
        }

        private static Claim toEntity(ClaimDto dto) {
            Claim result = new Claim();
            result.setId(dto.getClaimId());
            result.setEmail(dto.getEmail());
            result.setDescription(dto.getDescription());
            result.setStatus(dto.getStatus());
            result.setCreatedDate(dto.getCreatedDate());
            result.setIp(dto.getIp());
            return result;
        }
    }
}
