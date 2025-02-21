package artgallery.project.controller;

import artgallery.project.model.Auction;
import artgallery.project.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public List<Auction> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    @GetMapping("/{id}")
    public Optional<Auction> getAuctionById(@PathVariable Long id) {
        return auctionService.getAuctionById(id);
    }

    @PostMapping
    public Auction createAuction(@RequestBody Auction auction) {
        return auctionService.createOrUpdateAuction(auction);
    }

    @PutMapping("/{id}")
    public Auction updateAuction(@PathVariable Long id, @RequestBody Auction auctionDetails) {
        auctionDetails.setId(id);
        return auctionService.createOrUpdateAuction(auctionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuctionById(id);
    }

    // New: Get paginated auctions
    @GetMapping("/paginated")
    public ResponseEntity<List<Auction>> getPaginatedAuctions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Auction> paginatedAuctions = auctionService.getPaginatedAuctions(page, size).getContent();
        return new ResponseEntity<>(paginatedAuctions, HttpStatus.OK);
    }
    // New: Get paginated and sorted auctions
    @GetMapping("/sorted")
    public ResponseEntity<List<Auction>> getSortedPaginatedAuctions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        List<Auction> sortedPaginatedAuctions = auctionService.getSortedPaginatedAuctions(page, size, sortBy, direction).getContent();
        return new ResponseEntity<>(sortedPaginatedAuctions, HttpStatus.OK);
    }
    
}
