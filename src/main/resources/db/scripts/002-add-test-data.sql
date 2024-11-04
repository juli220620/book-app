--liquibase formatted sql
--changeset juli220620:add-test-data

insert into "books".book (name, author, description) values
                             ('One awesome book', 'Very talented author', 'This book is very good, I swear'),
                             ('Popular book', 'Author famous on Tik-Tok', 'Everyone loves this book and its tropes are hot'),
                             ('Boooring', 'Some scientist', 'The author may be smart but they can not write books'),
                             ('Bestseller by Stephen King', 'Stephen King', 'Everyone loves Stephen King, so the book is here'),
                             ('New horror by Stephen King', 'Stephen King', 'Everyone loves horrors, so the book is here'),
                             ('Please stop', 'Stephen King', 'Another book by King? Seriously? Please stop!');