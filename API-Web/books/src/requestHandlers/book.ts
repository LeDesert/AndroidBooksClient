import express, { Request, Response, NextFunction } from "express";
import { PrismaClient, Prisma } from "@prisma/client";
import { HttpError } from "../error";
import { NotFoundError } from "../HttpError";
import { lstat } from "fs";
import { BookCreationData } from "../validation/book";
import { assert } from "superstruct";

const prisma = new PrismaClient({
  datasources: {
    db: {
      url: "file:./dev.db",
    },
  },
});

const app = express();
const port = 3000;

app.use(express.json());

async function getAnAuthor(authorId: number) {
  const result = await prisma.author.findUnique({
    where: { id: authorId },
    select: {
      firstname: true,
      lastname: true,
    },
  });
  return result;
}

async function getAllBooks() {
  const result = await prisma.book.findMany({
    //skip: 1,
    //take: 5,
    include: {
      author: { select: { id: true, lastname: true } },
    },
  });
  return result;
}

async function getABook(bookId: number) {
  const result = await prisma.book.findUnique({
    where: { id: bookId },
    select: {
      id: true,
      name: true,
      authorId: true,
    },
  });
  return result;
}

async function getAllBooksFromAuthor(authorIdParam: number) {
  if (getAnAuthor(authorIdParam) != null) {
    const getBooks = await prisma.book.findMany({
      //skip: 1,
      //take: 5,
      where: {
        authorId: authorIdParam,
      },
      include: {
        author: { select: { id: true, lastname: true } },
      },
    });
    return getBooks;
  } else {
    throw new NotFoundError("Author not found");
  }
}

export async function get_all(req: Request, res: Response) {
  //GET /books : retourne la liste des livres
  const filter = req.query.name;
  if (filter != null) {
    console.log("filter " + filter);

    const result = await prisma.book.findMany({
      //skip: 1,
      //take: 5,
      where: { name: { contains: filter.toString() } },
      orderBy: {
        name: "asc",
      },
      include: {
        author: { select: { id: true, lastname: true } },
      },
    });
    res.json(result);
  } else {
    res.json(await getAllBooks());
  }
}

export async function get_one(req: Request, res: Response) {
  //  GET /books/:book_id : retourne le livre dont l'identifiant est :book_id
  const resp = await getABook(parseInt(req.params.book_id));
  if (resp != null) res.status(200).json(resp);
  else throw new NotFoundError("Author not found");
}

export async function get_all_of_author(req: Request, res: Response) {
  //GET /authors/:author_id/books : retourne la liste des livres associés à l'auteur dont l'identifiant est :author_id
  const filter = req.query.name;
  if (filter != null) {
    console.log("filter " + filter);

    const result = await prisma.book.findMany({
      //skip: 1,
      //take: 5,
      where: {
        name: { contains: filter.toString() },
        authorId: parseInt(req.params.author_id),
      },
      orderBy: {
        name: "asc",
      },
      include: {
        author: { select: { id: true, lastname: true } },
      },
    });
    res.json(result);
  } else {
    res.json(await getAllBooksFromAuthor(parseInt(req.params.author_id)));
  }
}

export async function create_one_of_author(req: Request, res: Response) {
  //POST /authors/:author_id/books : crée un nouveau livre associé à l'auteur dont l'identifiant est :author_id
  console.log("methode appelée");
  console.log(req.body.name + " " + req.body.publication_year);
  assert(req.body, BookCreationData);
  console.log("assert passe");
  try {
    const book = await prisma.book.create({
      data: {
        ...req.body,
        author: {
          connect: {
            id: Number(req.params.author_id),
          },
        },
      },
    });
    res.status(201).json(book);
  } catch (err: unknown) {
    console.error(err);
    throw new NotFoundError("Author not found");
  }
}

export async function update_one(req: Request, res: Response) {
  //PATCH /books/:book_id : met à jour le livre dont l'identifiant est :book_id
  assert(req.body, BookCreationData);
  const { name } = req.body;
  if (!name) {
    res.status(400).json({ error: "Name of the book is required" });
    return;
  }
  try {
    const updateBook = await prisma.book.update({
      where: {
        id: parseInt(req.params.book_id),
      },
      data: {
        name,
      },
    });
    res.status(201).json(updateBook);
  } catch (err: unknown) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new NotFoundError("Book not found");
    }
    console.error(err);
    throw new NotFoundError("Book not found");
  }
}

export async function delete_one(req: Request, res: Response) {
  //DELETE /books/:book_id : supprime le livre dont l'identifiant est :book_id
  try {
    const deleteBook = await prisma.book.delete({
      where: {
        id: parseInt(req.params.book_id),
      },
    });
    res.status(201).json("Book has been deleted");
  } catch (err: unknown) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new NotFoundError("Book not found");
    }
    console.error(err);
    throw new NotFoundError("Book not found");
  }
}

export async function get_all_comments_on_book(req: Request, res: Response) {
  //  GET /books/:book_id/comments : retourne le livre dont l'identifiant est :book_id
  if (getABook(parseInt(req.params.book_id)) != null) {
    const resp = await prisma.comment.findMany({
      //skip: 1,
      //take: 5,
      where: {
        bookId: parseInt(req.params.book_id),
      },
    });
    if (resp != null) res.status(200).json(resp);
    else throw new NotFoundError("Book not found");
  }
}

export async function get_all_ratings_on_book(req: Request, res: Response) {
  //  GET /books/:book_id/ratings : retourne le livre dont l'identifiant est :book_id
  if (getABook(parseInt(req.params.book_id)) != null) {
    const resp = await prisma.rating.findMany({
      //skip: 1,
      //take: 5,
      where: {
        bookId: parseInt(req.params.book_id),
      },
    });
    if (resp != null) res.status(200).json(resp);
    else throw new NotFoundError("Book not found");
  }
}
