package al.lhind.tab.claim.controller;

import al.lhind.tab.claim.exception.CustomException;
import al.lhind.tab.claim.model.ui.ClaimDto;
import al.lhind.tab.claim.service.ClaimService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/claims")
@Slf4j
public class ClaimController {
    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/{id}")
    public ClaimDto getClaim(@PathVariable Long id) throws CustomException {
        return claimService.getClaim(id);
    }

    @PostMapping
    public ResponseEntity<ClaimDto> createClaim(@Validated @RequestBody ClaimDto claimDto,  HttpServletRequest request) {
        log.info("Inside createClaim with ip {}", request.getRemoteAddr());
        claimDto = claimService.createClaim(claimDto, request.getRemoteAddr());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(claimDto.getClaimId())
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
