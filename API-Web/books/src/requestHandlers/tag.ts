import express, { Request, Response, NextFunction } from "express";
import { PrismaClient, Prisma } from "@prisma/client";
import { NotFoundError } from "../HttpError";

const prisma = new PrismaClient({
  datasources: {
    db: {
      url: "file:./dev.db",
    },
  },
});

async function getAllTags() {
  const result = await prisma.tag.findMany();
  return result;
}
async function getATag(tag_id: number) {
  const result = await prisma.tag.findUnique({
    where: { id: tag_id },
    select: {
      id: true,
      name: true,
    },
  });
  return result;
}
async function getATagByName(tag_name: string) {
  const result = await prisma.tag.findUnique({
    where: { name: tag_name },
    select: {
      id: true,
      name: true,
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

async function getAllTagsFromBook(bookIdParam: number) {
  if (getABook(bookIdParam) != null) {
    const getTags = await prisma.book.findMany({
      where: { id: bookIdParam },
      select: {
        Tags: true,
      },
    });
    return getTags;
  } else {
    throw new NotFoundError("Books not found");
  }
}

async function disconnectTagFromBook(bookIdParam: number, tagIdParam: number) {
  const result = await prisma.book.update({
    where: {
      id: bookIdParam,
    },
    data: {
      Tags: {
        disconnect: [{ id: tagIdParam }],
      },
    },
    include: {
      Tags: true,
    },
  });
}

//GET /tags : retourne la liste des tags
export async function get_all(req: Request, res: Response) {
  res.json(await getAllTags());
}

export async function get_books_by_tag(req: Request, res: Response) {
  try {
    const tagId = parseInt(req.params.id_tags);

    if (isNaN(tagId)) {
      res.status(400).json({ error: "Invalid tag ID." });
      return;
    }

    const books = await prisma.book.findMany({
      where: {
        Tags: {
          some: {
            id: tagId,
          },
        },
      },
      select: {
        id: true,
        name: true,
        publicationYear: true,
        author: {
          select: {
            id: true,
            lastname: true,
          },
        },
      },
    });

    if (books.length === 0) {
      res.status(404).json({ error: "No books found for this tag." });
      return;
    }

    res.status(200).json(books);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: "An error occurred while fetching books." });
  }
}

//GET /tags/:tag_id : retourne le tag dont l'identifiant est :tag_id
export async function get_one(req: Request, res: Response) {
  const resp = await getATag(parseInt(req.params.book_id));
  if (resp != null) res.status(200).json(resp);
  else throw new NotFoundError("Tag not found");
}

//GET /books/:book_id/tags : retourne la liste des tags associés au livre dont l'identifiant est :book_id
export async function get_all_of_book(req: Request, res: Response) {
  //GET /authors/:author_id/books : retourne la liste des livres associés à l'auteur dont l'identifiant est :author_id
  res.json(await getAllTagsFromBook(parseInt(req.params.book_id)));
}

export async function create_one(req: Request, res: Response) {
  //POST /tags : crée un nouveau tag
  try {
    const { name } = req.body;
    if (!name) {
      res.status(400).json({ error: "Name of the tag is required." });
      return;
    }
    if ((await getATagByName(name)) != null) {
      res.status(400).json({ error: "A tag with this name already exists." });
      return;
    }

    const newTag = await prisma.tag.create({
      data: {
        name,
      },
    });
    res.status(201).json(newTag);
  } catch (err: unknown) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new Error("Could not create a new tag");
    }
    console.error(err);
    throw new Error("Could not create a new tag");
  }
}

export async function update_one(req: Request, res: Response) {
  //PATCH /tags/:tag_id : met à jour le tag dont l'identifiant est :tag_id
  const { name } = req.body;
  if (!name) {
    res.status(400).json({ error: "New name of the book is required" });
    return;
  }

  if ((await getATag(parseInt(req.params.tag_id))) != null) {
    res.status(400).json({ error: "A tag with this id doesn't exists." });
    return;
  }

  try {
    const updateTag = await prisma.book.update({
      where: {
        id: parseInt(req.params.tag_id),
      },
      data: {
        name,
      },
    });
    res.status(201).json(updateTag);
  } catch (err: unknown) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new NotFoundError("Tag not found");
    }
    console.error(err);
    throw new NotFoundError("Tag not found");
  }
}

export async function delete_one(req: Request, res: Response) {
  //DELETE /tags/:tag_id : supprime le tag dont l'identifiant est :tag_id
  try {
    const deleteTag = await prisma.tag.delete({
      where: {
        id: parseInt(req.params.tag_id),
      },
    });
    res.status(201).json("Tag has been deleted");
  } catch (err: unknown) {
    if (
      err instanceof Prisma.PrismaClientKnownRequestError &&
      err.code === "P2025"
    ) {
      throw new NotFoundError("Tag not found");
    }
    console.error(err);
    throw new NotFoundError("Tag not found");
  }
}

export async function link_one(req: Request, res: Response) {
  //POST /books/:book_id/tags/:tag_id : associe le tag dont l'identifiant est :tag_id au livre dont l'identifiant est :book_id

  const result = await prisma.book.update({
    where: {
      id: parseInt(req.params.book_id),
    },
    data: {
      Tags: {
        connect: { id: parseInt(req.params.tag_id) },
      },
    },
    include: {
      Tags: true,
    },
  });
}

export async function unlink_one(req: Request, res: Response) {
  //DELETE /books/:book_id/tags/:tag_id : supprime l'association entre le tag dont l'identifiant est :tag_id et le livre dont l'identifiant est :book_id
  await disconnectTagFromBook(
    parseInt(req.params.book_id),
    parseInt(req.params.tag_id)
  );
}
