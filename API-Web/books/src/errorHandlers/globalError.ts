import { StructError } from "superstruct";

export class GlobalError extends Error {
  status?: number; // optionnel, afin de rester compatible avec le type Error standard

  constructor(err: Error) {
    super();
    if (err instanceof StructError) {
      err.status = 400;
      err.message = `Bad value for field ${err.key}`;
    }
  }
}
