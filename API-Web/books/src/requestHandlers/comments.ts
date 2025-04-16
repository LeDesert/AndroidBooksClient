import express, { Request, Response, NextFunction } from "express";
import { PrismaClient, Prisma } from "@prisma/client";

import { auth_client } from "./user";
import { expressjwt, Request as AuthRequest } from "express-jwt";

const prisma = new PrismaClient({
  datasources: {
    db: {
      url: "file:./dev.db",
    },
  },
});

const app = express();
const port = 3000;
const jwt = require("jsonwebtoken");

app.use(express.json());

export async function create_one(req: AuthRequest, res: Response) {
  try {
    const { content } = req.body;
    if (!content) {
      res.status(400).json({ error: "A content is required" });
      return;
    }
    const theUserId = jwt.verify(req.auth, process.env.JWT_SECRET);
    const newComment = await prisma.comment.create({
      data: {
        content,
        userId: theUserId,
        bookId: parseInt(req.params.book_id),
      },
    });
    res.status(201).json(newComment);
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}
export async function update_one(req: Request, res: Response) {
  try {
    const { content } = req.body;
    if (!content) {
      res.status(400).json({ error: "A content is required" });
      return;
    }

    const newCommentFinding = await prisma.comment.findUnique({
      where: {
        id: parseInt(req.params.comment_id),
      },
    });
    if (
      newCommentFinding?.userId ==
      jwt.verify(auth_client, process.env.JWT_SECRET)
    ) {
      const newComment = await prisma.comment.update({
        where: {
          id: parseInt(req.params.comment_id),
        },
        data: {
          content,
        },
      });
      res.status(201).json(newComment);
    }
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}

export async function delete_one(req: Request, res: Response) {
  try {
    const newCommentFinding = await prisma.comment.findUnique({
      where: {
        id: parseInt(req.params.comment_id),
      },
    });
    if (
      newCommentFinding?.userId ==
      jwt.verify(auth_client, process.env.JWT_SECRET)
    ) {
      const newComment = await prisma.comment.delete({
        where: {
          id: parseInt(req.params.comment_id),
        },
      });
      res.status(201).json(newComment);
    }
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}
