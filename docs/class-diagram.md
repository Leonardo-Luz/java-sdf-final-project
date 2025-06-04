## Class diagrams

```txt

    ┌─────────────────────────────────┐
    │ Review                          │◀───────────────────────*─*─────┐
    *─────────────────────────────────*                        │ │     │
    │ - id: String                    │                        │ │     │
    │ - title: String                 │                        │ │     │
    │ - text: String                  │                        │ │     │
    │ - readStartDate: LocalDate      │                        │ │     │
    │ - readEndDate: LocalDate        │                        │ │     │
    │ - book: Book                    │◆───────────────┐       │ │     │
    │ - user: User                    │◆───────┐       │       │ │     │
    │ - likes: List<User>             │        │       │       │ │     │
    *─────────────────────────────────*        │       │       │ │     │
    │ + getId(): String               │        │       │       │ │     │
    │ + setId()                       │        │       │       │ │     │
    │ + getTitle(): String            │        │       │       │ │     │
    │ + setTitle()                    │        │       │       │ │     │
    │ + getText(): String             │        │       │       │ │     │
    │ + setText()                     │        │       │       │ │     │
    │ + getReadStartDate(): LocalDate │        │       │       │ │     │
    │ + setReadStartDate()            │        │       │       │ │     │
    │ + getReadEndDate(): LocalDate   │        │       │       │ │     │
    │ + setReadEndDate()              │        │       │       │ │     │
    │ + getBook(): Book               │        │       │       │ │     │
    │ + setBook()                     │        │       │       │ │     │
    │ + getUser(): User               │        │       │       │ │     │
    │ + setUser()                     │        │       │       │ │     │
    │ + getLikes(): List<User>        │        │       │       │ │     │
    │ + setLikes()                    │        │       │       │ │     │
    │ + AddLike(): boolean            │        │       │       │ │     │
    └─────────────────────────────────┘        │       │       │ │     │
                                               │       │       │ │     │
    ┌─────────────────────────────┐            │       │       │ │     │
    │ Badge                       │◀───────────│──┐    │       │ │     │
    *─────────────────────────────*            │  │    │       │ │     │
    │ - id: String                │            │  │    │       │ │     │
    │ - name: String              │            │  │    │       │ │     │
    │ - requirements: String      │            │  │    │       │ │     │
    │ - users: List<User>         │◊───────┐   │  │    │       │ │     │
    *─────────────────────────────*        │   │  │    │       │ │     │
    │ + getId(): String           │        │   │  │    │       │ │     │
    │ + setId()                   │        │   │  │    │       │ │     │
    │ + getName(): String         │        │   │  │    │       │ │     │
    │ + setName()                 │        │   │  │    │       │ │     │
    │ + getRequirements(): String │        │   │  │    │       │ │     │
    │ + setRequirements()         │        │   │  │    │       │ │     │
    │ + getUsers(): List<User>    │        │   │  │    │       │ │     │
    │ + setUsers()                │        │   │  │    │       │ │     │
    └─────────────────────────────┘        │   │  │    │       │ │     │
                                           │   │  │    │       │ │     │
    ┌──────────────────────────────┐       │   │  │    │       │ │     │
    │ User                         │◀──────*───┘  │    │       │ │     │
    *──────────────────────────────*              │    │       │ │     │
    │ - id: String                 │              │    │       │ │     │
    │ - name: String               │              │    │       │ │     │
    │ - email: String              │              │    │       │ │     │
    │ - password: String           │              │    │       │ │     │
    │ - birthday: LocalDate        │              │    │       │ │     │
    │ - likes: List<Review>        │◊──────────────────────────┘ │     │
    │ - reviews: List<Review>      │◊────────────────────────────┘     │
    │ - badges: List<Badge>        │◊─────────────┘    │               │
    *──────────────────────────────*                   │               │
    │ + getId(): String            │                   │               │
    │ + setId()                    │                   │               │
    │ + getName(): String          │                   │               │
    │ + setName()                  │                   │               │
    │ + getEmail(): String         │                   │               │
    │ + setEmail()                 │                   │               │
    │ + getPassword(): String      │                   │               │
    │ + setPassword()              │                   │               │
    │ + getBirthday(): LocalDate   │                   │               │
    │ + setBirthday()              │                   │               │
    │ + getLikes(): List<Review>   │                   │               │
    │ + setLikes()                 │                   │               │
    │ + getReviews(): List<Review> │                   │               │
    │ + setReviews()               │                   │               │
    │ + getBadges(): List<Badge>   │                   │               │
    │ + setBadges()                │                   │               │
    └──────────────────────────────┘                   │               │
                                                       │               │
    ┌──────────────────────────────┐                   │               │
    │ Book                         │◀──────────────────┘               │
    *──────────────────────────────*                                   │
    │ - id: String                 │                                   │
    │ - name: String               │                                   │
    │ - pages: Integer             │                                   │
    │ - synopisis: String          │                                   │
    │ - reviews: List<Review>      │◊──────────────────────────────────┘
    *──────────────────────────────*
    │ + getId(): String            │
    │ + setId()                    │
    │ + getName(): String          │
    │ + setName()                  │
    │ + getPages(): Integer        │
    │ + setPages()                 │
    │ + getSynopisis(): String     │
    │ + setSynopisis()             │
    │ + getReviews(): List<Review> │
    │ + setReviews()               │
    └──────────────────────────────┘

```
