export interface SpecificationResponse {
    id: string;
    userId: string;
    specName: string;
    specs: {
      [key: string]: {
        startPos: number;
        endPos: number;
        dataType: string;
      };
    };
  }