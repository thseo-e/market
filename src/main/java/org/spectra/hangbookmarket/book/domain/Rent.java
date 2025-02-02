package org.spectra.hangbookmarket.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.spectra.hangbookmarket.user.domain.Users;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*대여 관리 도메인*/
public class Rent
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @Comment("대여 번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    @Comment("대여한 책")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "rented_user")
    @Comment("대여한 유저")
    private Users rentedUser;

    @ManyToOne
    @JoinColumn(name = "updated_user")
    private Users updatedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Comment("대여/반납/연체")
    private RentStatus status;

    @Column(name = "rent_date")
    @Comment("대여일")
    private LocalDateTime rentDate;

    @Column(name = "due_date")
    @Comment("반납 예정일")
    private LocalDateTime dueDate;

    @Builder
    public Rent(Book book, Users rentedUser)
    {
        this.book = book;
        this.rentedUser = rentedUser;
        this.updatedUser = rentedUser;
        this.status = RentStatus.RENTED;
        this.rentDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusDays(14);

    }

    public static Rent rented(Book book, Users rentedUser) {
        Rent rent = Rent.builder()
                .book(book)
                .rentedUser(rentedUser)
                .build();

        book.rented(rent);

        rentedUser.addRentedHistory(rent);

        return rent;
    }
}
