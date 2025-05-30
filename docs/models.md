- books ( <u>id</u>, name, pages, synopisis )

- reviews ( <u>id</u>, title, text, readStartDate, readEndDate, book_id, user_id )
    - book_id Referencia books
    - user_id Referencia users

- likes ( <u>user_id</u>, <u>review_id</u> )
    - user_id Referencia users
    - review_id Referencia reviews

- users ( <u>id</u>, name, email, password, birthday )

- badges ( <u>id</u>, name, requirements )

- user_badge ( <u>user_id</u>, <u>badge_id</u> )
    - user_id Referencia users
    - badge_id Referencia badges
