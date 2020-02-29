import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform {

  transform(items: any[], searchText: string): any {
    if (!items) {
      return null;
    }
    if (searchText !== undefined) {
      return items.filter((item: Object) =>
        (!item["name"]) ? item["username"].toLowerCase()
          .includes(searchText.toLowerCase()) : item["name"].toLowerCase()
            .includes(searchText.toLowerCase())
      );
    } else {
      return items;
    }
  }

}
