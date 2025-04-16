import express, { Request, Response, NextFunction } from "express";
import { PrismaClient, Prisma } from "@prisma/client";

import { expressjwt, Request as AuthRequest } from "express-jwt";

export const auth_client = [
  expressjwt({
    secret: process.env.JWT_SECRET as string,
    algorithms: ["HS256"],
  }),
  async (req: AuthRequest, res: Response, next: NextFunction) => {
    console.log(req.auth);
    const user = await prisma.user.findUnique({
      where: { id: Number(req.auth) },
    });
    if (user) {
      req.auth = user;
      next();
    } else {
      res.status(401).send("Invalid token");
    }
  },
];

const validator = require("validator");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

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

export async function create_one(req: Request, res: Response) {
  try {
    const { email, password, username } = req.body;
    if (!email || !password || !username) {
      res
        .status(400)
        .json({ error: "Email, password and username are required" });
      return;
    }
    if (!validator.isEmail(email)) {
      res.status(400).json({ error: "Email is not valid" });
      return;
    }

    const passwordHashed = await bcrypt.hash(password, 12);

    const newUser = await prisma.user.create({
      data: {
        email,
        password: passwordHashed,
        username,
      },
    });
    res.status(201).json(newUser);
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}

export async function connect_one(req: Request, res: Response) {
  try {
    const { email, password } = req.body;
    if (!email || !password) {
      res.status(400).json({ error: "Email and password are required" });
      return;
    }

    const user = await prisma.user.findUnique({
      where: {
        email: email,
      },
    });
    if (!user) {
      res.status(404).json({ error: "User not found" });
      return;
    }
    if (bcrypt.compare(password, user.password)) {
      res.status(200).json({ key: jwt.sign(user.id, process.env.JWT_SECRET) });
    } else {
      res.status(200).json({ message: "Password incorrect" });
    }
  } catch (err) {
    console.error(err);
    throw new Error("Une erreur est survenue");
  }
}
