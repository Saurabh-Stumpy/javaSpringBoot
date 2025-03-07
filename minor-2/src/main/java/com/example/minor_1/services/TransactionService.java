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

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public String initiateTxn(InitiateTransactionRequest request, Integer adminId) throws Exception {

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
        return request.getTransactionType() == TransactionType.ISSUE ? issuance(request, adminId) : returnBook(request, adminId);
    }

    private String issuance(InitiateTransactionRequest request, Integer adminId) throws Exception {
        Student student = studentService.find(request.getStudentId());
        Admin admin = adminService.find(adminId);
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

    private  String returnBook(InitiateTransactionRequest request, Integer adminId) throws Exception {
        Student student = studentService.find(request.getStudentId());
        Admin admin = adminService.find(adminId);
        List<Book> bookList = bookService.find("id",String.valueOf(request.getBookId()));

        Book book = bookList !=null && bookList.size() >0 ? bookList.get(0) : null;

        if(student == null
                || admin == null  
                || book == null
                || book.getStudent() == null    // If the book is assigned to someone or not
                || !book.getStudent().getId().equals(student.getId())){   // If tnhe book is assigned to the same student which is requesting to return the book
            throw new Exception("Invalid Exception");
        }

        // Getting the corresponding issuance txn
        Transaction issuanceTransaction =  transactionRepository
                .findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(student
                        ,book
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

    public void payFine(Integer amount, Integer studentId, String txnId) throws Exception {
        Transaction transaction = transactionRepository.findByTxnId(txnId);

        Book book = transaction.getBook();

        if(transaction.getFine() == amount
                && book.getStudent() != null
                && book.getStudent().getId() == studentId){
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            book.setStudent(null);
            bookService.createOrUpdate(book);
            transactionRepository.save(transaction);
        }else{
            throw new Exception("Invalid request");
        }

    }
}
