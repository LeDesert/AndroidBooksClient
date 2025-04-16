export type Author = {
  id: number;
  firstname: string;
  lastname: string;
  books?: Book[];
};

export type AuthorCreationData = {
  firstname: string;
  lastname: string;
};

export type Book = {
  id: number;
  name: string;
  author?: Author;
  publicationYear?: number;
  tags?: Tag[];
  comments?: Comment[];
  ratings?: Rating[];
};

export type BookCreationData = {
  name: string;
  authorId?: number;
  publicationYear?: number;
  tagIds?: number[];
};

export type Tag = {
  id: number;
  name: string;
  books?: Book[];
};

export type User = {
  id: number;
  email: string;
  password: string;
  username?: string;
  comments?: Comment[];
  ratings?: Rating[];
};

export type Comment = {
  id: number;
  content: string;
  user: User;
  book: Book;
  createdAt: Date;
  updatedAt: Date;
};

export type Rating = {
  id: number;
  value: number;
  user: User;
  book: Book;
};
