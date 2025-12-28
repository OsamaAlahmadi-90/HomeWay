package com.example.homeway.Controller;

import com.example.homeway.DTO.In.OfferUpdateDTOIn;
import com.example.homeway.Model.Offer;
import com.example.homeway.Model.User;
import com.example.homeway.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/admin/get-all")
    public ResponseEntity<List<Offer>> getAllOffersAdmin(User user) {
        return ResponseEntity.status(200).body(offerService.getAllOffers(user));
    }

    @PutMapping("/admin/update/{offerId}")
    public ResponseEntity<String> updateOfferAdmin(User user, @PathVariable Integer offerId, @RequestBody @Valid OfferUpdateDTOIn dto) {
        offerService.updateOffer(user, offerId, dto);
        return ResponseEntity.status(200).body("Offer updated");
    }

    @DeleteMapping("/admin/delete/{offerId}")
    public ResponseEntity<String> deleteOfferAdmin(User user, @PathVariable Integer offerId) {
        offerService.deleteOffer(user, offerId);
        return ResponseEntity.status(200).body("Offer deleted");
    }

    @GetMapping("/customer/get-my-offers")
    public ResponseEntity<List<Offer>> getMyOffersCustomer(User user) {
        return ResponseEntity.status(200).body(offerService.getMyOffersCustomer(user));
    }
}