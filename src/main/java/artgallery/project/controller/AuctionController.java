package artgallery.project.controller;

import artgallery.project.model.Auction;
import artgallery.project.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        return auctionService.getAuctionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        Auction createdAuction = auctionService.createOrUpdateAuction(auction);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable Long id, @RequestBody Auction auctionDetails) {
        if (!auctionService.getAuctionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        auctionDetails.setId(id);
        Auction updatedAuction = auctionService.createOrUpdateAuction(auctionDetails);
        return ResponseEntity.ok(updatedAuction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuction(@PathVariable Long id) {
        if (!auctionService.getAuctionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        auctionService.deleteAuctionById(id);
        return ResponseEntity.ok("Auction deleted successfully");
    }

    // Delete by name
    @DeleteMapping("/delete/name")
    public ResponseEntity<String> deleteAuctionByName(@RequestParam String name) {
        boolean deleted = auctionService.deleteAuctionByName(name);
        return deleted ? ResponseEntity.ok("Auction deleted successfully") : ResponseEntity.notFound().build();
    }

    // Delete by date
    @DeleteMapping("/delete/date")
    public ResponseEntity<String> deleteAuctionByDate(@RequestParam String date) {
        boolean deleted = auctionService.deleteAuctionByDate(date);
        return deleted ? ResponseEntity.ok("Auction deleted successfully") : ResponseEntity.notFound().build();
    }

    // Get paginated auctions
    @GetMapping("/paginated")
    public ResponseEntity<List<Auction>> getPaginatedAuctions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Auction> paginatedAuctions = auctionService.getPaginatedAuctions(page, size).getContent();
        return ResponseEntity.ok(paginatedAuctions);
    }

    // Get paginated and sorted auctions
    @GetMapping("/sorted")
    public ResponseEntity<List<Auction>> getSortedPaginatedAuctions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        List<Auction> sortedPaginatedAuctions = auctionService.getSortedPaginatedAuctions(page, size, sortBy, direction).getContent();
        return ResponseEntity.ok(sortedPaginatedAuctions);
    }

    // Search auctions by name
    @GetMapping("/search/name")
    public ResponseEntity<List<Auction>> findAuctionsByName(@RequestParam String name) {
        List<Auction> auctions = auctionService.findAuctionsByName(name);
        return ResponseEntity.ok(auctions);
    }

    // Search auctions by date
    @GetMapping("/search/date")
    public ResponseEntity<List<Auction>> findAuctionsByDate(@RequestParam String date) {
        List<Auction> auctions = auctionService.findAuctionsByDate(date);
        return ResponseEntity.ok(auctions);
    }

    // Update auction by name
    @PutMapping("/update/name")
    public ResponseEntity<String> updateAuctionByName(
            @RequestParam String oldName,
            @RequestParam String newName,
            @RequestParam String date
    ) {
        boolean updated = auctionService.updateAuctionByName(oldName, newName, date);
        return updated ? ResponseEntity.ok("Auction updated successfully") : ResponseEntity.notFound().build();
    }
}
