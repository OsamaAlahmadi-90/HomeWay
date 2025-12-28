package com.example.homeway.Service;

import com.example.homeway.Repository.CompanyRepository;
import com.example.homeway.Repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.homeway.DTO.In.OfferUpdateDTOIn;
import com.example.homeway.API.ApiException;
import com.example.homeway.Model.Offer;
import com.example.homeway.Model.User;
import com.example.homeway.Repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public List<Offer> getAllOffers(User user) {
        if (user == null) {
            throw new ApiException("unauthorized");
        }
        return offerRepository.findAll();
    }

    public void updateOffer(User user, Integer offerId, OfferUpdateDTOIn dto) {
        if (user == null) {
            throw new ApiException("unauthorized");
        }

        Offer offer = offerRepository.findOfferById(offerId);
        if (offer == null) {
            throw new ApiException("offer not found with id: " + offerId);
        }

        String status = dto.getStatus();
        if (!status.equalsIgnoreCase("PAID") && !status.equalsIgnoreCase("NOT_PAID")) {
            throw new ApiException("status must be PAID or NOT_PAID");
        }

        offer.setPrice(dto.getPrice());
        offer.setStatus(status);

        offerRepository.save(offer);
    }

    public void deleteOffer(User user, Integer offerId) {
        if (user == null) {
            throw new ApiException("unauthorized");
        }

        Offer offer = offerRepository.findOfferById(offerId);
        if (offer == null) {
            throw new ApiException("offer not found with id: " + offerId);
        }

        offerRepository.delete(offer);
    }

    public List<Offer> getMyOffersCustomer(User user) {
        if (user == null) {
            throw new ApiException("unauthorized");
        }

        if (user.getCustomer() == null) {
            throw new ApiException("customer profile not found");
        }

        Integer customerId = user.getCustomer().getId();
        return offerRepository.findAllByRequest_Customer_Id(customerId);
    }

}
