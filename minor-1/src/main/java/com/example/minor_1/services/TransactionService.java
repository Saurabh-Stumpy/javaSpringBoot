package com.example.minor_1.services;


import com.example.minor_1.dtos.InitiateTransactionRequest;
import com.example.minor_1.models.*;
import com.example.minor_1.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @Value("${student.allowed.max-book}") // From application properties
    Integer maxBooksAllowed;

    @Value("${student.allowed.duration}")
    Integer duration;

    public String initiateTxn(InitiateTransactionRequest request) throws Exception {

        /*
        * Issue
        * 1. If the book is available and student is valid.
        * 2. Entry in transaction.
        * 3. If student has reached the maximum limit of books.
        * 4. Book to be assigned to a student ==> update in book table.
         */
        /*
        * Return
        * 1. If the book is valid or not and the student is valid or not.
        * 2. Entry in the Txn table.
        * 3. Due date check and fine calculation.
        * 4. If there is no fine then de-allocate the book from the
        *    student name (book table).
        */
        return request.getTransactionType() == TransactionType.ISSUE ? issuance(request) : returnBook(request);
    }

    private String issuance(InitiateTransactionRequest request) throws Exception {
        Student student = studentService.find(request.getStudentId());
        Admin admin = adminService.find(request.getAdminId());
        List<Book> bookList = bookService.find("id",String.valueOf(request.getBookId()));

        Book book = bookList !=null && bookList.size() >0 ? bookList.get(0) : null;

        if(student == null
                || admin == null
                || book == null
                || book.getStudent() != null
                || student.getBookList().size() >= maxBooksAllowed){
            throw new Exception("Invalid Exception");
        }

        Transaction transaction = null;
        try {
            transaction = Transaction.builder()
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(request.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();
            transactionRepository.save(transaction);

            book.setStudent(student);

            bookService.createOrUpdate(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        } catch (Exception e) {
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }finally {
            transactionRepository.save(transaction);
        }

        return transaction.getTxnId();
    }

    private  String returnBook(InitiateTransactionRequest request) throws Exception {
        Student student = studentService.find(request.getStudentId());
        Admin admin = adminService.find(request.getAdminId());
        List<Book> bookList = bookService.find("id",String.valueOf(request.getBookId()));

        Book book = bookList !=null && bookList.size() >0 ? bookList.get(0) : null;

        if(student == null
                || admin == null
                || book == null
                || book.getStudent() == null    // If the book is assigned to someone or not
                || book.getStudent().getId() != student.getId()){   // If tnhe book is assigned to the same student which is requesting to return the book
            throw new Exception("Invalid Exception");
        }

        // Getting the corresponding issuance txn
        Transaction issuanceTransaction =  transactionRepository
                .findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(student.getId()
                        ,book.getId()
                        ,TransactionType.ISSUE);

        if(issuanceTransaction == null){
            throw new Exception("Invalid request");
        }

        Transaction transaction = null;

        try {

            Integer fine = calculateFine(issuanceTransaction.getCreatedOn());

            transaction = Transaction.builder()
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(request.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .fine(fine)
                    .build();

            transactionRepository.save(transaction);

            if (fine == 0) {
                book.setStudent(null);
                bookService.createOrUpdate(book);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            }
        } catch (Exception e) {
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }finally {
            transactionRepository.save(transaction);
        }
        return transaction.getTxnId();

    }

    private Integer calculateFine(Date issuanceTime){
        long issueTimeInMillis = issuanceTime.getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        long diff = currentTimeInMillis - issueTimeInMillis;

        long daysPassed = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

        if(daysPassed > duration){
            return (int)(daysPassed - duration);
        }
        return 0;

    }
}
