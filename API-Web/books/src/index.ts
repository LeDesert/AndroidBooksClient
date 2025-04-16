import express, { Request, Response, NextFunction } from "express";
import { PrismaClient, Prisma } from "@prisma/client";
import { HttpError } from "./error";
import { NotFoundError } from "./HttpError";
import * as author from "./requestHandlers/author";
import * as book from "./requestHandlers/book";
import * as tag from "./requestHandlers/tag";
import * as user from "./requestHandlers/user";
import * as comments from "./requestHandlers/comments";
import * as rating from "./requestHandlers/rating";

import { auth_client } from "./requestHandlers/user"; // Assurez-vous que le chemin est correct

import { object, optional, refine, string } from "superstruct";
import { isInt } from "validator";
import { assert } from "superstruct";
import cors from "cors";

export const ReqParams = object({
  author_id: optional(refine(string(), "int", (value: string) => isInt(value))),
  book_id: optional(refine(string(), "int", (value: string) => isInt(value))),
});

const validateParams = (req: Request, res: Response, next: NextFunction) => {
  assert(req.params, ReqParams);
  next();
};

const prisma = new PrismaClient({
  datasources: {
    db: {
      url: "file:./dev.db",
    },
  },
});

const app = express();
const port = 3000;

app.use(cors());

app.use((req: Request, res: Response, next: NextFunction) => {
  res.header("Access-Control-Expose-Headers", "X-Total-Count");
  next();
});

app.use(express.json());

app.get("/", (req: Request, res: Response) => {
  throw new HttpError("Author not found", 404);
});

app.get("/books/:book_id/comments", book.get_all_comments_on_book);
app.post("/books/:book_id/comments", auth_client, comments.create_one);
app.patch("/comments/:comment_id", auth_client, comments.update_one);
app.delete("/comments/:comment_id", auth_client, comments.delete_one);

//app.get("/authors", author.get_all);
app.get("/authors", author.get_all_Filtered);
app.get("/books", book.get_all);

app.post("/authors", author.create_one);
app.post("/authors/:author_id/books", book.create_one_of_author);
app.get("/authors/:author_id/books", book.get_all_of_author);

app.get("/books/:book_id/tags", tag.get_all_of_book);

app.get("/books/tags/:id_tags", tag.get_books_by_tag);

app.post("/signup", user.create_one);
app.post("/signin", user.connect_one);

app.get("/books/:book_id/ratings/average", rating.get_average);
app.get("/books/:book_id/ratings", book.get_all_ratings_on_book);
app.get("/books/:book_id/ratings", auth_client, rating.create_one);
app.patch("/ratings/:comment_id", auth_client, rating.update_one);
app.delete("/ratings/:comment_id", auth_client, rating.delete_one);

app
  .route("/tags/:tag_id")
  .all(validateParams)
  .get(tag.get_one)
  .patch(tag.update_one)
  .delete(tag.delete_one);

app
  .route("/tags") //
  .all(validateParams)
  .get(tag.get_all)
  .post(tag.create_one);

app
  .route("/books/:book_id/tags/:tag_id")
  .post(tag.link_one)
  .delete(tag.unlink_one);

app
  .route("/authors/:author_id")
  .all(validateParams)
  .get(author.get_one)
  .patch(author.update_one)
  .delete(author.delete_one);

app
  .route("/books/:book_id")
  .all(validateParams)
  .get(book.get_one)
  .patch(book.update_one)
  .delete(book.delete_one);

app.listen(port, () => {
  console.log(`App listening on port ${port}`);
});

app.use((err: HttpError, req: Request, res: Response, next: NextFunction) => {
  res.status(err.status ?? 500).send(err.message);
});
