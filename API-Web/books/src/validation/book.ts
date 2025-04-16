import { object, string, size } from "superstruct";
import { integer, optional, refine, enums } from "superstruct";

export const BookCreationData = object({
  name: size(string(), 1, 50),
  publicationYear: optional(integer()),
});
