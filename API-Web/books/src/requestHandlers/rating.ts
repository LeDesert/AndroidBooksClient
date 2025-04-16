import express, { Request, Response, NextFunction } from "express";
import { PrismaClient } from "@prisma/client";

import { auth_client } from "./user";

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

export async function create_one(req: Request, res: Response) {
  try {
    const { value } = req.body;
    if (!value) {
      res.status(400).json({ error: "A value is required" });
      return;
    }
    if (value < 0 || value > 5) {
      res.status(400).json({ error: "The value has to be between 0 and 5" });
      return;
    }
    const theUserId = jwt.verify(auth_client, process.env.JWT_SECRET);
    const newRating = await prisma.rating.create({
      data: {
        value: parseInt(value),
        userId: theUserId,
        bookId: parseInt(req.params.book_id),
      },
    });
    res.status(201).json(newRating);
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}

export async function update_one(req: Request, res: Response) {
  try {
    const { value } = req.body;
    if (!value) {
      res.status(400).json({ error: "A content is required" });
      return;
    }

    const newratingFinding = await prisma.rating.findUnique({
      where: {
        id: parseInt(req.params.rating_id),
      },
    });
    if (
      newratingFinding?.userId ==
      jwt.verify(auth_client, process.env.JWT_SECRET)
    ) {
      const newrating = await prisma.rating.update({
        where: {
          id: parseInt(req.params.rating_id),
        },
        data: {
          value,
        },
      });
      res.status(201).json(newrating);
    }
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}

export async function delete_one(req: Request, res: Response) {
  try {
    const newratingFinding = await prisma.rating.findUnique({
      where: {
        id: parseInt(req.params.rating_id),
      },
    });
    if (
      newratingFinding?.userId ==
      jwt.verify(auth_client, process.env.JWT_SECRET)
    ) {
      const newrating = await prisma.rating.delete({
        where: {
          id: parseInt(req.params.rating_id),
        },
      });
      res.status(201).json(newrating);
    }
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}

export async function get_average(req: Request, res: Response) {
  try {
    const average = await prisma.rating.aggregate({
      _avg: {
        value: true,
      },
      where: {
        bookId: parseInt(req.params.book_id),
      },
    });

    res.status(201).json(average);
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}
