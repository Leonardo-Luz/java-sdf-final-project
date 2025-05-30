- books ( id, name, pages, synopisis )

- reviews ( id, title, text, readStartDate, readEndDate, book_id, user_id )
    - book_id Referencia books
    - user_id Referencia users

- likes ( user_id, review_id )
    - user_id Referencia users
    - review_id Referencia reviews

- users ( id, name, email, password, birthday )

- badges ( id, name, requirements )

- user_badge ( user_id, badge_id )
    - user_id Referencia users
    - badge_id Referencia badges
