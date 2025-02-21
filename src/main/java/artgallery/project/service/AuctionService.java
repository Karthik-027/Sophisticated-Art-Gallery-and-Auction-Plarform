package artgallery.project.service;

import artgallery.project.model.Auction;
import artgallery.project.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    // Get all auctions
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    // Get auction by ID
    public Optional<Auction> getAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    // Create or update auction
    public Auction createOrUpdateAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    // Delete auction by ID
    public void deleteAuctionById(Long id) {
        auctionRepository.deleteById(id);
    }

    // Get paginated auctions
    public Page<Auction> getPaginatedAuctions(int page, int size) {
        return auctionRepository.findAll(PageRequest.of(page, size));
    }

    // Get paginated and sorted auctions
    public Page<Auction> getSortedPaginatedAuctions(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return auctionRepository.findAll(pageable);
    }
}
