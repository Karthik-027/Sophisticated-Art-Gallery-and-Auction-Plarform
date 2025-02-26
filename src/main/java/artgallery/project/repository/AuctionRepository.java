package artgallery.project.repository;

import artgallery.project.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // Find auctions by name (case-insensitive)
    @Query("SELECT a FROM Auction a WHERE LOWER(a.auctionName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Auction> findByName(@Param("name") String name);

    // Find auctions by exact date
    @Query("SELECT a FROM Auction a WHERE a.date = :auctionDate")
    List<Auction> findByDate(@Param("auctionDate") String auctionDate);

    // Update auction details by auction name
    @Transactional
    @Modifying
    @Query("UPDATE Auction a SET a.date = :newDate, a.location = :newLocation WHERE LOWER(a.auctionName) = LOWER(:name)")
    int updateAuctionByName(@Param("name") String name, @Param("newDate") String newDate, @Param("newLocation") String newLocation);

    // Update auction details by auction date
    @Transactional
    @Modifying
    @Query("UPDATE Auction a SET a.auctionName = :newName, a.location = :newLocation WHERE a.date = :auctionDate")
    int updateAuctionByDate(@Param("auctionDate") String auctionDate, @Param("newName") String newName, @Param("newLocation") String newLocation);

    // Delete auction by auction name
    @Transactional
    @Modifying
    @Query("DELETE FROM Auction a WHERE LOWER(a.auctionName) = LOWER(:name)")
    int deleteAuctionByName(@Param("name") String name);

    // Delete auction by auction date
    @Transactional
    @Modifying
    @Query("DELETE FROM Auction a WHERE a.date = :auctionDate")
    int deleteAuctionByDate(@Param("auctionDate") String auctionDate);
}
