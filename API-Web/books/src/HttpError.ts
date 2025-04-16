import {HttpError} from './error';

export class NotFoundError extends HttpError {
    constructor(message: string) {
      super(message, 404);
    }
  }
  