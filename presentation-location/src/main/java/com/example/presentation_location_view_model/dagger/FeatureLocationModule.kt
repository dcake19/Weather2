package com.example.presentation_location_view_model.dagger

import com.example.domain.LocationInteractor
import com.example.domain.LocationsRepository
import com.example.domain.autocomplete.AutoCompleteRepository
import com.example.domain.autocomplete.PredictionInteractor
import com.example.presentation_location_view_model.locations.LocationsMapper
import com.example.presentation_location_view_model.locations.LocationsView
import com.example.presentation_location_view_model.locations.LocationsViewModel
import com.example.presentation_location_view_model.locations.LocationsViewModelImpl
import com.example.presentation_location_view_model.search.SearchLocationMapper
import com.example.presentation_location_view_model.search.SearchLocationViewModel
import com.example.presentation_location_view_model.search.SearchLocationViewModelImpl
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class FeatureLocationModule {

    @Provides
    @FeatureLocationScope
    fun provideLocationViewModel(locationInteractor: LocationInteractor,
                                 rxSchedulerProvider: RxSchedulerProvider,
                                 locationsMapper: LocationsMapper): LocationsViewModel {
        val locationsViewModelEmitter = ViewModelEmitter<List<LocationsView>>(true)

        return LocationsViewModelImpl(locationInteractor,rxSchedulerProvider,
            locationsMapper,locationsViewModelEmitter)
    }

    @Provides
    @FeatureLocationScope
    fun provideLocationMapper(): LocationsMapper = LocationsMapper()

    @Provides
    @FeatureLocationScope
    fun provideLocationInteractor(locationsRepository: LocationsRepository): LocationInteractor{
        return LocationInteractor(locationsRepository)
    }

    @Provides
    @FeatureLocationScope
    fun provideSearchLocationViewModel(interactor: PredictionInteractor,
                                       rxSchedulerProvider: RxSchedulerProvider,
                                       mapper: SearchLocationMapper): SearchLocationViewModel{
        return SearchLocationViewModelImpl(interactor,rxSchedulerProvider,mapper,
            ViewModelEmitter(),ViewModelEmitter()
        )
    }

    @Provides
    @FeatureLocationScope
    fun providePredictionInteractor(autoCompleteRepository: AutoCompleteRepository,
                                    locationsRepository: LocationsRepository): PredictionInteractor{
        return PredictionInteractor(autoCompleteRepository,locationsRepository)
    }

    @Provides
    @FeatureLocationScope
    fun provideSearchLocationMapper() = SearchLocationMapper()
}