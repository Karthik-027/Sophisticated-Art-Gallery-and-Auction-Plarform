package artgallery.project.service;

import artgallery.project.model.Auction;
import artgallery.project.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void deleteAuctionById(Long id) {
        auctionRepository.deleteById(id);
    }

    // Delete auction by name
    @Transactional
    public boolean deleteAuctionByName(String name) {
        int deletedCount = auctionRepository.deleteAuctionByName(name);
        return deletedCount > 0;
    }

    // Delete auction by date
    @Transactional
    public boolean deleteAuctionByDate(String auctionDate) {
        int deletedCount = auctionRepository.deleteAuctionByDate(auctionDate);
        return deletedCount > 0;
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

    // Find auctions by name
    public List<Auction> findAuctionsByName(String name) {
        return auctionRepository.findByName(name);
    }

    // Find auctions by date
    public List<Auction> findAuctionsByDate(String date) {
        return auctionRepository.findByDate(date);
    }

    // Update auction by name
    @Transactional
    public boolean updateAuctionByName(String oldName, String newDate, String newLocation) {
        int updatedCount = auctionRepository.updateAuctionByName(oldName, newDate, newLocation);
        return updatedCount > 0;
    }

    // Update auction by date
    @Transactional
    public boolean updateAuctionByDate(String auctionDate, String newName, String newLocation) {
        int updatedCount = auctionRepository.updateAuctionByDate(auctionDate, newName, newLocation);
        return updatedCount > 0;
    }
}
