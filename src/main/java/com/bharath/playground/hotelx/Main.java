package com.bharath.playground.hotelx;

import graphql.schema.queries.SearchQuery;
import graphql.schema.hotelx.HotelCriteriaSearchInput;
import graphql.schema.hotelx.RoomInput;
import graphql.schema.hotelx.PaxInput;
import graphql.schema.hotelx.HotelSettingsInput;

import java.time.LocalDate;

import static java.util.Collections.*;

public class Main
{

  private static String ENDPOINT = "https://api.travelgatex.com/";
  public static void main(String[] args)
  {
    // $token: String, $criteria: HotelCriteriaSearchInput, $settings: HotelSettingsInput, $filter: FilterInput,
    //    $filterSearch: HotelXFilterSearchInput

    PaxInput paxInput = PaxInput.builder(30).build();
    RoomInput roomInput = RoomInput.builder(singletonList(paxInput)).build();
    HotelCriteriaSearchInput criteria = HotelCriteriaSearchInput.builder(
      LocalDate.of(2019,12, 12),
      LocalDate.of(2019,12, 13), singletonList(roomInput))
      .withHotels(singletonList("1"))
      .build();

    HotelSettingsInput settings = HotelSettingsInput.builder()
      .withClient("Demo_Client")
      .withTestMode(true)
      .withContext("HOTELTEST")
      .build();

    SearchQuery query = SearchQuery.builder()
      .withCriteria(criteria)
      .withSettings(settings)
      .build();

    System.out.println(query.write().toJson());
    SearchQuery.Result result = query.request(ENDPOINT)
//      .withAuthorization("Apikey", "64780338-49c8-4439-7c7d-d03c2033b145")
      .withHeader("Authorization", "Apikey 64780338-49c8-4439-7c7d-d03c2033b145")
      .withHeader("Content-Type", "application/json")
      .post();
    System.out.println(result.write().toJson());
  }
}
