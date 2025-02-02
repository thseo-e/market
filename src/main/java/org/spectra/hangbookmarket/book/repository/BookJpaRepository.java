package org.spectra.hangbookmarket.book.repository;

import org.spectra.hangbookmarket.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Long>
{
}
