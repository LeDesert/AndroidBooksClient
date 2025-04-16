import express, { Request, Response, NextFunction } from "express";
import { PrismaClient, Prisma } from "@prisma/client";
import { NotFoundError } from "../HttpError";
import { GlobalError } from "../errorHandlers/globalError";

import { assert } from "superstruct";
import { AuthorCreationData } from "../validation/author";
import { StructError } from "superstruct";

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

async function getAllAuthors() {
  const result = await prisma.author.findMany();
  return result;
}

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

export async function get_all(req: Request, res: Response) {
  res.json(await getAllAuthors());
}

export async function get_one(req: Request, res: Response) {
  const resp = await getAnAuthor(parseInt(req.params.author_id));
  if (resp != null) res.status(200).json(resp);
  else throw new NotFoundError("Author not found");
}

export async function update_one(req: Request, res: Response) {
  assert(req.body, AuthorCreationData);
  const { firstname, lastname } = req.body;
  if (!firstname || !lastname) {
    res.status(400).json({ error: "Firstname and lastname are required" });
    return;
  }
  try {
    const updateUser = await prisma.author.update({
      where: {
        id: parseInt(req.params.author_id),
      },
      data: {
        firstname,
        lastname,
      },
    });
    res.status(201).json(updateUser);
  } catch (err: unknown) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new NotFoundError("Author not found");
    }
    console.error(err);
    throw new NotFoundError("Author not found");
  }
}

export async function create_one(req: Request, res: Response) {
  try {
    assert(req.body, AuthorCreationData);
    const { firstname, lastname } = req.body;
    if (!firstname || !lastname) {
      res.status(400).json({ error: "Firstname and lastname are required" });
      return;
    }

    const newAuthor = await prisma.author.create({
      data: {
        firstname,
        lastname,
      },
    });
    res.status(201).json(newAuthor);
  } catch (err) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new NotFoundError("Author not found");
    } else if (err instanceof StructError) {
      throw new Error("Bad value");
    }

    console.error(err);
    throw new NotFoundError("Author not found");
  }
}

export async function delete_one(req: Request, res: Response) {
  try {
    const deleteUser = await prisma.author.delete({
      where: {
        id: parseInt(req.params.author_id),
      },
    });
    res.status(201).json("User has been deleted");
  } catch (err: unknown) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new NotFoundError("Author not found");
    }
    console.error(err);
    throw new NotFoundError("Author not found");
  }
}

export async function get_all_Filtered(req: Request, res: Response) {
  const filterLastName = req.query.lastname;
  let filterBook: boolean = false;
  if (req.query.hasBooks) {
    filterBook = JSON.parse(req.query.hasBooks.toString());
  }
  if (filterLastName != null) {
    if (filterBook) {
      const authorsWithSomeBooks = await prisma.author.findMany({
        //skip: 1,
        //take: 5,
        where: {
          lastname: { contains: filterLastName.toString() },
          Book: {
            some: {},
          },
        },
        orderBy: {
          lastname: "asc",
        },
        include: {
          Book: { select: { id: true, name: true }, orderBy: { name: "asc" } },
        },
      });
      res.json(authorsWithSomeBooks);
    } else {
      console.log("filter " + filterLastName);

      const result = await prisma.author.findMany({
        //skip: 1,
        //take: 5,
        where: { lastname: { contains: filterLastName.toString() } },
        orderBy: {
          lastname: "asc",
        },
        include: {
          Book: { select: { id: true, name: true }, orderBy: { name: "asc" } },
        },
      });
      res.json(result);
    }
  } else if (filterBook) {
    const authorsWithSomeBooks = await prisma.author.findMany({
      //skip: 1,
      //take: 5,
      where: {
        Book: {
          some: {},
        },
      },
      include: {
        Book: { select: { id: true, name: true }, orderBy: { name: "asc" } },
      },
    });
    res.json(authorsWithSomeBooks);
  } else {
    const result = await getAllAuthors();
    res.json(result);
  }
}
