import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

const Tags = [
  { name: "Novel" },
  { name: "Essay" },
  { name: "Short Story" },
  { name: "Theater" },
  { name: "Historical" },
  { name: "Biography" },
  { name: "Poetry" },
  { name: "Mystery" },
  { name: "Polar" },
  { name: "Drama" },
  { name: "Action" },
  { name: "Comedy" },
  { name: "Tragedy" },
  { name: "Adventure" },
  { name: "Fantasy" },
  { name: "Science-Fiction" },
  { name: "Thriller" },
  { name: "Horror" },
  { name: "Romance" },
  { name: "Children" },
  { name: "Comics" },
  { name: "Manga" },
  { name: "Cooking" },
  { name: "Travel" },
  { name: "Art" },
  { name: "Religion" },
  { name: "Science" },
  { name: "Computer" },
  { name: "Business" },
  { name: "Sport" },
  { name: "Music" },
];

const authors = [
  {
    firstname: "William",
    lastname: "Shakespeare",
    Book: {
      create: [
        {
          name: "Romeo and Juliet",
          publicationYear: 1597,
          Tags: {
            connect: [
              { name: "Drama" },
              { name: "Romance" },
              { name: "Tragedy" },
            ],
          },
        },
        {
          name: "Hamlet",
          publicationYear: 1603,
          Tags: {
            connect: [{ name: "Drama" }, { name: "Tragedy" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Charles",
    lastname: "Dickens",
    Book: {
      create: [
        {
          name: "Oliver Twist",
          publicationYear: 1837,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Historical" },
              { name: "Adventure" },
            ],
          },
        },
        {
          name: "Great Expectations",
          publicationYear: 1861,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Historical" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "J. R. R.",
    lastname: "Tolkien",
    Book: {
      create: [
        {
          name: "The Hobbit",
          publicationYear: 1937,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Adventure" },
              { name: "Fantasy" },
            ],
          },
        },
        {
          name: "The Lord of the Rings",
          publicationYear: 1954,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Adventure" },
              { name: "Fantasy" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Antoine de",
    lastname: "Saint-Exupéry",
    Book: {
      create: [
        {
          name: "Le Petit Prince",
          publicationYear: 1943,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Short Story" },
              { name: "Adventure" },
              { name: "Fantasy" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Jules",
    lastname: "Verne",
    Book: {
      create: [
        {
          name: "Vingt mille lieues sous les mers",
          publicationYear: 1870,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Adventure" },
              { name: "Science-Fiction" },
            ],
          },
        },
        {
          name: "Le Tour du monde en quatre-vingts jours",
          publicationYear: 1873,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Adventure" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Agatha",
    lastname: "Christie",
  },
  {
    firstname: "Stephen",
    lastname: "King",
    Book: {
      create: [
        {
          name: "It",
          publicationYear: 1986,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Horror" }],
          },
        },
        {
          name: "The Shining",
          publicationYear: 1977,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Horror" }],
          },
        },
      ],
    },
  },
  {
    firstname: "J. K.",
    lastname: "Rowling",
    Book: {
      create: [
        {
          name: "Harry Potter and the Philosopher's Stone",
          publicationYear: 1997,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Adventure" },
              { name: "Fantasy" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "George R. R.",
    lastname: "Martin",
    Book: {
      create: [
        {
          name: "A Game of Thrones",
          publicationYear: 1996,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Adventure" },
              { name: "Fantasy" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Victor",
    lastname: "Hugo",
    Book: {
      create: [
        {
          name: "Les Misérables",
          publicationYear: 1862,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Historical" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Alexandre",
    lastname: "Dumas",
    Book: {
      create: [
        {
          name: "Les Trois Mousquetaires",
          publicationYear: 1844,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Historical" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Arthur",
    lastname: "Conan Doyle",
    Book: {
      create: [
        {
          name: "A Study in Scarlet",
          publicationYear: 1887,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Mystery" }],
          },
        },
        {
          name: "The Hound of the Baskervilles",
          publicationYear: 1902,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Mystery" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Oscar",
    lastname: "Wilde",
    Book: {
      create: [
        {
          name: "The Picture of Dorian Gray",
          publicationYear: 1890,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Mystery" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Jane",
    lastname: "Austen",
    Book: {
      create: [
        {
          name: "Pride and Prejudice",
          publicationYear: 1813,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Romance" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Mark",
    lastname: "Twain",
    Book: {
      create: [
        {
          name: "The Adventures of Tom Sawyer",
          publicationYear: 1876,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Adventure" }],
          },
        },
        {
          name: "Adventures of Huckleberry Finn",
          publicationYear: 1884,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Adventure" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Ernest",
    lastname: "Hemingway",
  },
  {
    firstname: "F. Scott",
    lastname: "Fitzgerald",
  },
  {
    firstname: "H. G.",
    lastname: "Wells",
    Book: {
      create: [
        {
          name: "The War of the Worlds",
          publicationYear: 1898,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
        {
          name: "The Time Machine",
          publicationYear: 1895,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Mary",
    lastname: "Shelley",
    Book: {
      create: [
        {
          name: "Frankenstein",
          publicationYear: 1818,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Horror" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Edgar Allan",
    lastname: "Poe",
  },
  {
    firstname: "Honoré de",
    lastname: "Balzac",
    Book: {
      create: [
        {
          name: "Le Père Goriot",
          publicationYear: 1835,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Historical" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Gustave",
    lastname: "Flaubert",
  },
  {
    firstname: "Marcel",
    lastname: "Proust",
    Book: {
      create: [
        {
          name: "À la recherche du temps perdu",
          publicationYear: 1913,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Adventure" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Franz",
    lastname: "Kafka",
    Book: {
      create: [
        {
          name: "The Metamorphosis",
          publicationYear: 1915,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Short Story" },
              { name: "Mystery" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Fiodor",
    lastname: "Dostoïevski",
  },
  {
    firstname: "Léon",
    lastname: "Tolstoï",
  },
  {
    firstname: "Miguel de",
    lastname: "Cervantès",
  },
  {
    firstname: "Isaac",
    lastname: "Asimov",
    Book: {
      create: [
        {
          name: "Foundation",
          publicationYear: 1951,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
        {
          name: "I, Robot",
          publicationYear: 1950,
          Tags: {
            connect: [
              { name: "Short Story" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "George",
    lastname: "Orwell",
    Book: {
      create: [
        {
          name: "1984",
          publicationYear: 1949,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
        {
          name: "Animal Farm",
          publicationYear: 1945,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Adventure" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Lewis",
    lastname: "Carroll",
    Book: {
      create: [
        {
          name: "Alice's Adventures in Wonderland",
          publicationYear: 1865,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Adventure" },
              { name: "Fantasy" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Roald",
    lastname: "Dahl",
    Book: {
      create: [
        {
          name: "Charlie and the Chocolate Factory",
          publicationYear: 1964,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Adventure" },
              { name: "Children" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "H. P.",
    lastname: "Lovecraft",
    Book: {
      create: [
        {
          name: "The Call of Cthulhu",
          publicationYear: 1928,
          Tags: {
            connect: [{ name: "Short Story" }, { name: "Horror" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Rudyard",
    lastname: "Kipling",
  },
  {
    firstname: "Robert Louis",
    lastname: "Stevenson",
    Book: {
      create: [
        {
          name: "Treasure Island",
          publicationYear: 1883,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Adventure" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Ian",
    lastname: "Fleming",
  },
  {
    firstname: "Arthur C.",
    lastname: "Clarke",
    Book: {
      create: [
        {
          name: "2001: A Space Odyssey",
          publicationYear: 1968,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Ray",
    lastname: "Bradbury",
    Book: {
      create: [
        {
          name: "Fahrenheit 451",
          publicationYear: 1953,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Frank",
    lastname: "Herbert",
    Book: {
      create: [
        {
          name: "Dune",
          publicationYear: 1965,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Dan",
    lastname: "Simmons",
    Book: {
      create: [
        {
          name: "Hyperion",
          publicationYear: 1989,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Douglas",
    lastname: "Adams",
    Book: {
      create: [
        {
          name: "The Hitchhiker's Guide to the Galaxy",
          publicationYear: 1979,
          Tags: {
            connect: [
              { name: "Novel" },
              { name: "Science-Fiction" },
              { name: "Adventure" },
              { name: "Comedy" },
            ],
          },
        },
      ],
    },
  },
  {
    firstname: "Albert",
    lastname: "Camus",
    Book: {
      create: [
        {
          name: "L'Étranger",
          publicationYear: 1942,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Mystery" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Emile",
    lastname: "Zola",
    Book: {
      create: [
        {
          name: "Germinal",
          publicationYear: 1885,
          Tags: {
            connect: [{ name: "Novel" }, { name: "Historical" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Charles",
    lastname: "Baudelaire",
    Book: {
      create: [
        {
          name: "Les Fleurs du mal",
          publicationYear: 1857,
          Tags: {
            connect: [{ name: "Poetry" }],
          },
        },
      ],
    },
  },
  {
    firstname: "Guy de",
    lastname: "Maupassant",
  },
];

async function main() {
  for (const tag of Tags) {
    await prisma.tag.create({
      data: tag,
    });
  }
  for (const author of authors) {
    await prisma.author.create({
      data: author,
    });
  }
}

main()
  .then(async () => {
    await prisma.$disconnect();
  })
  .catch(async (e) => {
    console.error(e);
    await prisma.$disconnect();
    process.exit(1);
  });
